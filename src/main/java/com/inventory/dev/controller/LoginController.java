package com.inventory.dev.controller;

import com.inventory.dev.entity.MenuEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.exception.BadRequestException;
import com.inventory.dev.model.mapper.UserMapper;
import com.inventory.dev.model.payload.JwtAuthenticationResponse;
import com.inventory.dev.model.request.LoginReq;
import com.inventory.dev.security.CustomUserDetails;
import com.inventory.dev.security.JwtTokenUtil;
import com.inventory.dev.service.UserService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class LoginController {
    //7ngay
    private static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private LoginValidator loginValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == UserEntity.class) {
            binder.setValidator(loginValidator);
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new UserEntity());
        return "login/login";
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req, HttpServletResponse response) {
//        // Authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );
            // Gen token
            String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());
            System.out.println(token);
            // Add token to cookie to login
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setMaxAge(MAX_AGE_COOKIE);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(UserMapper.toUserDto(((CustomUserDetails)  authentication.getPrincipal()).getUserEntity()));
        } catch (Exception ex) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        }
    }

    @PostMapping("/processLogin")
    public String processLogin(Model model, @ModelAttribute("loginForm") @Validated UserEntity users, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login/login";
        }

        UserEntity user = userService.findByProperty("userName", users.getUsername()).get(0);
//        UserRoleEntity userRole =(UserRoleEntity) user.getRoles().iterator().next();
//        List<MenuEntity> menuList = new ArrayList<>();
//        RoleEntity role = userRole.getRole();
//        List<MenuEntity> menuChildList = new ArrayList<>();
//        for(Object obj : role.getAuths()) {
//            AuthEntity auth = (AuthEntity) obj;
//            MenuEntity menu = auth.getMenu();
//            if(menu.getParentId()==0 && menu.getOrderIndex()!=-1 && menu.getActiveFlag()==1 && auth.getPermission()==1 && auth.getActiveFlag()==1) {
//                menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
//                menuList.add(menu);
//            }else if( menu.getParentId()!=0 && menu.getOrderIndex()!=-1 && menu.getActiveFlag()==1 && auth.getPermission()==1 && auth.getActiveFlag()==1) {
//                menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
//                menuChildList.add(menu);
//            }
//        }
//        for(MenuEntity menu : menuList) {
//            List<MenuEntity> childList = new ArrayList<>();
//            for(MenuEntity childMenu : menuChildList) {
//                if(childMenu.getParentId()== menu.getId()) {
//                    childList.add(childMenu);
//                }
//            }
//            menu.setChild(childList);
//        }
//        sortMenu(menuList);
//        for(MenuEntity menu : menuList) {
//            sortMenu(menu.getChild());
//        }
//        session.setAttribute(Constant.MENU_SESSION, menuList);
//        session.setAttribute(Constant.USER_INFO, user);
        return "redirect:/index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Constant.MENU_SESSION);
        session.removeAttribute(Constant.USER_INFO);
        return "redirect:/login";
    }

    private void sortMenu(List<MenuEntity> menus) {
        Collections.sort(menus, new Comparator<MenuEntity>() {
            @Override
            public int compare(MenuEntity o1, MenuEntity o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
    }
}
