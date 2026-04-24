package boberbackend.service;

import boberbackend.jpa.model.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> findByEmail(String email);

}

