package com.example.demo.auth;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }
    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "reda",
                        passwordEncoder.encode("pass"),
                        STUDENT.getGrantedAuthotitie(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "faycal",
                        passwordEncoder.encode("pass"),
                        ADMIN.getGrantedAuthotitie(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "yazid",
                        passwordEncoder.encode("pass"),
                        ADMINTRAINEE.getGrantedAuthotitie(),
                        true,
                        true,
                        true,
                        true
                )


        );
        return applicationUsers;
    }


}
