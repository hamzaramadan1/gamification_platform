package com.game.gamification_platform.security;

import com.game.gamification_platform.model.Role;
import com.game.gamification_platform.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/api/authentication/**")
                .permitAll()
                .antMatchers("/api/superadmin/**").hasRole(Role.SUPERADMIN.name())
                .antMatchers("/api/user/user/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/user/update").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/admin").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/user/admin/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/course/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/minigame/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/memory_game/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/question/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/card/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/answer/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/puzzle_game/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/word_search/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/userminigamescore/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/userMemoryGameScore/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/userPuzzleGameScore/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name(), Role.SUPERADMIN.name())
                .antMatchers("/image/**").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/image").hasAnyRole(Role.ADMIN.name(), Role.SUPERADMIN.name())
                .antMatchers("/api/**").authenticated();

        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
}
