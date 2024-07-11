package com.example.userservice.business.concretes;

import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
public class UserManager {

    @Autowired
    private UsersResource usersResource;

    @Autowired
    private RealmResource realmResource;

    public void createUser(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());

        userRepresentation.setCredentials(Collections.singletonList(credential));

        Response response = usersResource.create(userRepresentation);
        if (response.getStatus() == 201) {
            System.out.println("User created successfully");
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRoles(userId, user.getRoles());
        } else {
            System.out.println("Failed to create user: " + response.getStatus());
        }
    }

    private void assignRoles(String userId, List<Role> roles) {
        for (Role role : roles) {
            RoleRepresentation roleRepresentation = realmResource.roles().get(role.name()).toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        }
    }

    public List<UserRepresentation> getAllUsers() {
        return usersResource.list();
    }

    public UserRepresentation getUserById(String userId) {
        return usersResource.get(userId).toRepresentation();
    }

    public void updateUser(String userId, User user) {
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmail());

        usersResource.get(userId).update(userRepresentation);
        assignRoles(userId, user.getRoles());
    }

    public void deleteUser(String userId) {
        usersResource.get(userId).remove();
    }
}
