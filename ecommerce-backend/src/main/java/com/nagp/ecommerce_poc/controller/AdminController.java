package com.nagp.ecommerce_poc.controller;

import com.nagp.ecommerce_poc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final RoleService roleService;

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ADMIN')")
    public String assignRole(@RequestParam String uid,
                             @RequestParam String role)
            throws Exception {

        roleService.assignRole(uid, role);
        return "Role Assigned Successfully";
    }
}
