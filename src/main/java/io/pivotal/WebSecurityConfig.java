package io.pivotal;

/**
 * Created by pivotal on 9/24/15.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Autowired
    UserRepository userRepository;
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager () {
        Properties users = new Properties();
        return new InMemoryUserDetailsManager(users);
    }

}