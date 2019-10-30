/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}
//        String contextPath = env.getProperty("management.context-path");
//        if (StringUtils.isEmpty(contextPath)) {
//            contextPath = "";
//        }
//http.authorizeRequests()
//        .antMatchers("/**/actuator/**","/**/actuator/").authenticated().anyRequest().permitAll();
//.and().httpBasic();
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

//        httpSecurity.csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and().httpBasic();
////https://www.javadevjournal.com/spring/basic-authentication-with-spring-security/
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authentication)
//            throws Exception
//    {
//        authentication.inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("nimda"))
//                .authorities("ROLE_USER");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
