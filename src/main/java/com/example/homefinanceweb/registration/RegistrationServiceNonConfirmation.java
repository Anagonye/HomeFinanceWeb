package com.example.homefinanceweb.registration;

import com.example.homefinanceweb.appuser.AppUser;
import com.example.homefinanceweb.appuser.AppUserRole;
import com.example.homefinanceweb.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;




@Service
@Profile("prod")
@AllArgsConstructor
public class RegistrationServiceNonConfirmation implements RegistrationServiceInterface {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    @Override
    public void register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator
                .test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException(String.format("email %s not valid", request.getEmail()));
        }

            appUserService.singUpUser(new AppUser(
                            request.getFirstName(),
                            request.getEmail(),
                            request.getPassword(),
                            AppUserRole.USER
                    )
            );

    }

    @Override
    public boolean confirmToken(String token) {

        return false;
    }


}
