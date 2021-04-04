package com.inventory.dev.controller;

import com.inventory.dev.entity.Paging;
import com.inventory.dev.entity.RoleEntity;
import com.inventory.dev.entity.UserEntity;
import com.inventory.dev.entity.UserRoleEntity;
import com.inventory.dev.exception.NotFoundException;
import com.inventory.dev.model.dto.UserDto;
import com.inventory.dev.model.mapper.UserMapper;
import com.inventory.dev.model.request.CreateUserReq;
import com.inventory.dev.security.CustomUserDetails;
import com.inventory.dev.security.JwtTokenUtil;
import com.inventory.dev.service.RoleService;
import com.inventory.dev.service.UserService;
import com.inventory.dev.validate.UserValidator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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

        UserEntity result = userService.saveUserJdbc(req);

        // Gen token
        UserDetails principal = new CustomUserDetails(result);
        String token = jwtTokenUtil.generateToken(principal);
//         Add token to cookie to login
        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(UserMapper.toUserDto(result));
    }


    @GetMapping(value = "/user/list/{page}")
    public ResponseEntity<?> showUsersList(UserEntity user, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<UserEntity> users = userService.getUsersList(user, paging);
        if (users == null) {
            throw new NotFoundException("Khong tim thay");
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id) {
        log.info("Edit user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            UserDto userDto = UserMapper.toUserDto(user);
            List<RoleEntity> roles = roleService.getRoleList(null, null);
            Map<String, String> mapRole = new HashMap<>();
            for (RoleEntity role : roles) {
                mapRole.put(String.valueOf(role.getId()), role.getRoleName());
            }
            UserRoleEntity userRole = user.getUserRoles().iterator().next();
            userDto.setRoleID(userRole.getRoles().getId());
            user = UserMapper.toUserEntity(userDto);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/user/view/{id}")
    public ResponseEntity<?> view(@PathVariable("id") int id) {
        log.info("View user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            List<RoleEntity> roles = roleService.getRoleList(null, null);
            Map<String, String> mapRole = new HashMap<>();
            for (RoleEntity role : roles) {
                mapRole.put(String.valueOf(role.getId()), role.getRoleName());
            }
        }
        return ResponseEntity.ok(results);
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserEntity user) {
        UserRoleEntity userRole = new UserRoleEntity();
        List<RoleEntity> roles = roleService.getRoleList(null, null);
//        UserRoleEntity userRole = user.getUserRoles().iterator().next();
        for (RoleEntity role : roles){
            userRole.setRoles(role);
        }
        if (user.getId() != null && user.getId() != 0) {
            try {
                userService.update(user);
                ResponseEntity.status(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        } else {
            try {
                userService.save(user);
                ResponseEntity.status(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        log.info("Delete user with id=" + id);
        List<UserEntity> results = userService.findByProperty("id", id);
        if (results != null && !results.isEmpty()) {
            UserEntity user = results.get(0);
            try {
                userService.deleteUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
