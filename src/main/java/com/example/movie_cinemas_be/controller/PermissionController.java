package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.PermissionRequest;
import com.example.movie_cinemas_be.dtos.response.PermissionResponse;
import com.example.movie_cinemas_be.entitys.Permission;
import com.example.movie_cinemas_be.service.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {

    private PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/create")
    public ResponseCustom<PermissionResponse> createPermission(PermissionRequest permission) {
        return ResponseCustom.<PermissionResponse>builder()
                .message("Permission created successfully")
                .data(permissionService.createPermission(permission))
                .build();
    }

    @GetMapping("/allPermissions")
    public ResponseCustom<List<PermissionResponse>> allPermissions() {
        return ResponseCustom.<List<PermissionResponse>>builder()
                .message("Permissions listed successfully")
                .data(permissionService.getAllPermission())
                .build();
    }
}
