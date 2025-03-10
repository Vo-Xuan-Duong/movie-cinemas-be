package com.example.movie_cinemas_be.controller;

import com.example.movie_cinemas_be.dtos.ResponseCustom;
import com.example.movie_cinemas_be.dtos.request.RoleRequest;
import com.example.movie_cinemas_be.dtos.response.RoleResponse;
import com.example.movie_cinemas_be.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getAll")
    public ResponseCustom<List<RoleResponse>>  getAll() {
        return ResponseCustom.<List<RoleResponse>>builder()
                .message("Success fully retrieved all roles")
                .code(200)
                .data(roleService.findAllRoles())
                .build();
    }

    @GetMapping("/roleByName/{nameRole}")
    public ResponseCustom<RoleResponse>  getRoleByName(@PathVariable String nameRole) {
        return ResponseCustom.<RoleResponse>builder()
                .message("Success fully retrieved role by name")
                .code(200)
                .data(roleService.findRoleByName(nameRole))
                .build();
    }

    @PostMapping("/create")
    public ResponseCustom<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ResponseCustom.<RoleResponse>builder()
                .message("Success fully retrieved role")
                .code(200)
                .data(roleService.createRole(roleRequest))
                .build();
    }

    @PutMapping("/update")
    public ResponseCustom<RoleResponse> updateRole(@RequestBody RoleRequest roleRequest) {
        return ResponseCustom.<RoleResponse>builder()
                .message("Success fully retrieved role")
                .code(200)
                .data(roleService.updateRole(roleRequest))
                .build();
    }

    @DeleteMapping("/delete/{nameRole}")
    public ResponseCustom<?> deleteRole(@PathVariable String nameRole) {
        roleService.deleteRoleByName(nameRole);
        return ResponseCustom.builder()
                .message("Success fully retrieved role")
                .code(200)
                .build();
    }
}
