package com.max.userdetail.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "Hello " + getUsername();
    }

    public String getUsername() {
        // 通过Security提供的SecurityContextHolder获取用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (ObjectUtils.isNotEmpty(principal)) {
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        } else {
            throw new RuntimeException("非法操作！用户不存在！");
        }
    }
}
