package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.model.mapper.UserMapper;
import com.inventory.dev.model.request.CreateUserReq;
import com.inventory.dev.security.CustomUserDetails;
import com.inventory.dev.security.JwtTokenUtil;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.service.UserService;
import com.inventory.dev.util.Constant;
import com.inventory.dev.validate.UserValidator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/.*")
@RequiredArgsConstructor
@Api(value = "User APIs")
public class UserController {
    static final Logger log = Logger.getLogger(UserController.class);
    //7ngay
    private static final int MAX_AGE_COOKIE = 7 * 24 * 60 * 60;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if (binder.getTarget().getClass() == UserEntity.class) {
            binder.setValidator(userValidator);
        }
    }


    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserReq req, HttpServletResponse response) {
        // Create user

        UserEntity result = userService.createUser(req);

        // Gen token
        UserDetails principal = new CustomUserDetails(result);
        String token = jwtTokenUtil.generateToken(principal);
        System.out.printf("toke" + token);
//         Add token to cookie to login
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.printf("cookie" + cookie);
        try {
            return ResponseEntity.ok(UserMapper.toUserDto(result));
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping(value = {"/user/list", "/user/list/"})
    public String redirect() {
        return "redirect:/user/list/1";
    }

    @GetMapping(value = "/user/list/{page}")
    public ResponseEntity<?> showUsersList(Model model, HttpSession session, UserEntity user, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<UserEntity> users = userService.getUsersList(user, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("users", users);
        System.out.println(model.toString());
        return ResponseEntity.ok().body(model.getAttribute("users"));

    }

    @PostMapping("/user/add")
    public ResponseEntity<?> add(Model model) {
        model.addAttribute("titlePage", "Add Users");
        model.addAttribute("modelForm", new UserEntity());
        List<RoleEntity> roles = roleService.getRoleList(null, null);
        Map<String, String> mapRole = new HashMap<>();
        for (RoleEntity role : roles) {
            mapRole.put(String.valueOf(role.getId()), role.getRoleName());
        }
        model.addAttribute("mapRole", mapRole);
        model.addAttribute("viewOnly", false);
        return ResponseEntity.ok().body(model.getAttribute("mapRole"));
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> edit(Model model, @PathVariable("id") int id) {
        log.info("Edit user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            List<RoleEntity> roles = roleService.getRoleList(null, null);
            Map<String, String> mapRole = new HashMap<>();
            for (RoleEntity role : roles) {
                mapRole.put(String.valueOf(role.getId()), role.getRoleName());
            }
//            UserRoleEntity userRole =(UserRoleEntity) user.getRoles().iterator().next();
//            user.setRoleId(userRole.getRole().getId());
            model.addAttribute("mapRole", mapRole);
            model.addAttribute("titlePage", "Edit Users");
            model.addAttribute("modelForm", user);
            model.addAttribute("viewOnly", false);
            model.addAttribute("editMode", true);
        }
        return ResponseEntity.ok().body(model.getAttribute("user"));
    }

    @GetMapping("/user/view/{id}")
    public ResponseEntity<?> view(Model model, @PathVariable("id") int id) {
        log.info("View user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            List<RoleEntity> roles = roleService.getRoleList(null, null);
            Map<String, String> mapRole = new HashMap<>();
            for (RoleEntity role : roles) {
                mapRole.put(String.valueOf(role.getId()), role.getRoleName());
            }
            model.addAttribute("mapRole", mapRole);
            model.addAttribute("titlePage", "View Users");
            model.addAttribute("modelForm", user);
            model.addAttribute("viewOnly", true);
            model.addAttribute("editMode", true);
        }
        return ResponseEntity.ok().body(model.getAttribute("user"));
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> save(Model model, @RequestBody @Validated UserEntity user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (user.getId() != null) {
                model.addAttribute("titlePage", "Edit Users");
                model.addAttribute("editMode", true);
            } else {
                model.addAttribute("titlePage", "Add Users");
            }
            List<RoleEntity> roles = roleService.getRoleList(null, null);
            Map<String, String> mapRole = new HashMap<>();
            for (RoleEntity role : roles) {
                mapRole.put(String.valueOf(role.getId()), role.getRoleName());
            }
            model.addAttribute("mapRole", mapRole);
            model.addAttribute("modelForm", user);
            model.addAttribute("viewOnly", false);
            return ResponseEntity.ok().body(model.getAttribute("user"));

        }

        //	UserRole userRole =(UserRole) user.getUserRoles().iterator().next();
        if (user.getId() != null && user.getId() != 0) {
            try {
                userService.update(user);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        } else {
            try {
                userService.save(user);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return ResponseEntity.ok().body(model.getAttribute("user"));

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> delete(Model model, @PathVariable("id") int id, HttpSession session) {
        log.info("Delete user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            try {
                userService.deleteUser(user);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return ResponseEntity.ok().body(results);
    }
}
