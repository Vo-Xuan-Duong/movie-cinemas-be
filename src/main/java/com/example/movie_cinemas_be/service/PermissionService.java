package com.example.movie_cinemas_be.service;

import com.example.movie_cinemas_be.dtos.request.PermissionRequest;
import com.example.movie_cinemas_be.dtos.response.PermissionResponse;
import com.example.movie_cinemas_be.entitys.Permission;
import com.example.movie_cinemas_be.reponsitory.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class PermissionService {

    private PermissionRepository permissionRepository;

    public  PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;

    }

    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = new Permission();
        permission.setName(permissionRequest.getName());
        permission.setDescription(permissionRequest.getDescription());
        return convertToPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permission -> convertToPermissionResponse(permission)).collect(Collectors.toList());
    }

    public void deletePermission(Permission permission) {
        permissionRepository.delete(permission);
    }


    public PermissionResponse convertToPermissionResponse(Permission permission) {
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setName(permission.getName());
        permissionResponse.setDescription(permission.getDescription());
        return permissionResponse;
    }
}
