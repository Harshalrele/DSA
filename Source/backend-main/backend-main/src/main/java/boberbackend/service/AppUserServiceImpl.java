/*
 * Bober Clinic note: Contains the main business logic for this service.
 * File: backend-main/backend-main/src/main/java/boberbackend/service/AppUserServiceImpl.java
 */
package boberbackend.service;

import boberbackend.jpa.model.AppUser;
import boberbackend.jpa.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }
}



