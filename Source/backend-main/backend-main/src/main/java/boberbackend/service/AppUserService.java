/*
 * Bober Clinic note: Defines the methods that this service must provide.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/AppUserService.java
 */
package boberbackend.service;

import boberbackend.jpa.model.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByEmail(String email);

}



