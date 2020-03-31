package com.alena.jewelryproject.spring;

import com.alena.jewelryproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                .and()
                    //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/registration")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
//        httpSecurity
//                .csrf()
//                    .disable()
//                .authorizeRequests()
//                    //Доступ только для не зарегистрированных пользователей
//                    .antMatchers("/registration").not().fullyAuthenticated()
//                    //Доступ только для пользователей с ролью Администратор
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    //Доступ разрешен всем пользователей
////                    .antMatchers("/", "/resources/**").permitAll()
//                //Все остальные страницы требуют аутентификации
//                .anyRequest().authenticated()
//                .and()
//                    //Настройка для входа в систему
//                    .formLogin()
//                    .loginPage("/registration")
//                    //Перенарпавление на главную страницу после успешного входа
//                    .defaultSuccessUrl("/")
//                    .permitAll()
//                .and()
//                    .logout()
//                    .permitAll()
//                    .logoutSuccessUrl("/");
    }
}
