package com.example.demo.security;

import com.example.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplactionSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder ;

    @Autowired
    public ApplactionSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http

              .csrf().disable()
              .authorizeRequests()
              .antMatchers("/", "index", "css/*", "/js/*").permitAll()
              .antMatchers("/api/**").hasRole(STUDENT.name())
            //.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
            //.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
              .anyRequest()
              .authenticated()
              .and()
              .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .passwordParameter("password") // le meme nom password dans la page login
                .usernameParameter("username")  // le meme nom username dans la page login
              .and()
              .rememberMe()//pour que la session se rappeler du compte default 2 week
                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                .key("somthingVerySecure")
                .rememberMeParameter("remember-me")// le meme de chechbox remember-me dans la page login
              .and()
              .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/login");
              
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails reda = User.builder()
                .username("reda")
                .password(passwordEncoder.encode("pass"))
     //           .roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthoritySet())
                .build();
        UserDetails faycal = User.builder()
                .username("faycal")
                .password(passwordEncoder.encode("pass"))
     //           .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthoritySet())
                .build();
        UserDetails hicham = User.builder()
                .username("hicham")
                .password(passwordEncoder.encode("pass"))
     //           .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthoritySet())
                .build();

        return new InMemoryUserDetailsManager(
                reda,
                faycal,
                hicham
        );

    }
}
