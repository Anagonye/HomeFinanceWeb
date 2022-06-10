package com.example.homefinanceweb.appuser;

import com.example.homefinanceweb.registration.token.ConfirmationToken;
import com.example.homefinanceweb.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND =
            "user wth email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND, email)));
    }

    public String singUpUser(AppUser appUser){
        boolean userExist = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        if(userExist){
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationtoken = new ConfirmationToken(
                token,
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationtoken);


        return token;
    }

    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }






    // Creating user for test
    @Bean
    CommandLineRunner commandLineRunner(){
        AppUser appUser = new AppUser();
        appUser.setFirstName("Jacek");
        appUser.setEmail("test@test.pl");
        String encodedPassword = bCryptPasswordEncoder
                .encode("test123");
        appUser.setPassword(encodedPassword);
        appUser.setAppUserRole(AppUserRole.USER);
        appUser.setEnabled(true);

        return args -> appUserRepository.save(appUser);
    }






}
