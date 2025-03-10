package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.RoleRequest;
import com.example.movie_cinemas_be.dtos.response.RoleResponse;
import com.example.movie_cinemas_be.entitys.Role;
import com.example.movie_cinemas_be.reponsitory.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final PermissionService permissionService;
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository, PermissionService permissionService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    public List<RoleResponse> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> convertToRoleResponse(role)).collect(Collectors.toList());
    }

    public RoleResponse createRole(RoleRequest role) {
        Role roleEntity = new Role();
        roleEntity.setName(role.getName());
        roleEntity.setDescription(role.getDescription());
        return convertToRoleResponse(roleRepository.save(roleEntity));
    }

    public RoleResponse updateRole(RoleRequest roleRequest) {
        Role role = roleRepository.findByName(roleRequest.getName());
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        return convertToRoleResponse(roleRepository.save(role));
    }

    public RoleResponse findRoleByName(String name) {
        return convertToRoleResponse(roleRepository.findByName(name));
    }

    public void deleteRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        roleRepository.delete(role);
    }


    public RoleResponse convertToRoleResponse(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setPermissions(role.getPermissions().stream().map(permission -> permissionService.convertToPermissionResponse(permission)).collect(Collectors.toList()));
        return roleResponse;
    }
}
