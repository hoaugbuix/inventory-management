package com.inventory.dev.config;


import com.inventory.dev.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfigInterceptor extends HandlerInterceptorAdapter  {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            modelAndView.addObject("user_firstName", principal.getUser().getFistName());
            modelAndView.addObject("user_lastName", principal.getUser().getLastName());
            modelAndView.addObject("user_avatar", principal.getUser().getAvatar());
            modelAndView.addObject("user_email", principal.getUser().getEmail());
            modelAndView.addObject("isLogined", true);
        } else {
            modelAndView.addObject("isLogined", false);
        }
    }
}
