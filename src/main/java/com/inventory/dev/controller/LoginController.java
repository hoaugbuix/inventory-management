package com.inventory.dev.controller;

import com.inventory.dev.entity.*;
import com.inventory.dev.exception.BadRequestException;
import com.inventory.dev.model.dto.MenuDto;
import com.inventory.dev.model.mapper.MenuMapper;
import com.inventory.dev.model.mapper.UserMapper;
import com.inventory.dev.model.request.LoginReq;
import com.inventory.dev.security.CustomUserDetails;
import com.inventory.dev.security.JwtTokenUtil;
import com.inventory.dev.service.UserService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.LoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
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

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req, HttpServletResponse response) {
        // Authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );
            // Gen token
            String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());
            // Add token to cookie to login
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setMaxAge(MAX_AGE_COOKIE);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(UserMapper.toUserDto(((CustomUserDetails) authentication.getPrincipal()).getUser()));
        } catch (Exception ex) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        }
    }


    @PostMapping("/processLogin")
    public ResponseEntity<?> processLogin(@Valid @RequestBody UserEntity users) {
        UserEntity user = userService.findByProperty("username", users.getUsername()).get(0);
        UserRoleEntity userRole = (UserRoleEntity) user.getRoles();
        List<MenuDto> menuList = new ArrayList<>();
        RoleEntity role = userRole.getRoles();
        List<MenuDto> menuChildList = new ArrayList<>();
        for (Object obj : role.getAuths()) {
            AuthEntity auth = (AuthEntity) obj;
            MenuEntity me = auth.getMenus();

            MenuDto menu = MenuMapper.toMenuDto(me);

            if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1 && auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
                menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
                menuList.add(menu);
            } else if (menu.getParentId() != 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1 && auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
                menu.setIdMenu(menu.getUrl().replace("/", "")+"Id");
                menuChildList.add(menu);
            }
        }
        for (MenuDto menu : menuList) {
            List<MenuDto> childList = new ArrayList<>();
            for (MenuDto childMenu : menuChildList) {
                if (childMenu.getParentId() == menu.getId()) {
                    childList.add(childMenu);
                }
            }
            menu.setChild(childList);
        }
        sortMenu(menuList);
        for (MenuDto menu : menuList) {
            sortMenu(menu.getChild());
        }
        return ResponseEntity.ok(menuList);
    }


    private void sortMenu(List<MenuDto> menus) {
        Collections.sort(menus, new Comparator<MenuDto>() {
            @Override
            public int compare(MenuDto o1, MenuDto o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
    }
}
