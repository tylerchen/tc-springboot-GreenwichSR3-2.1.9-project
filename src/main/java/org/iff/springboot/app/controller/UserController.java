/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.iff.springboot.app.entity.User;
import org.iff.springboot.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * UserController
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/add")
    public User add() {
        long time = System.currentTimeMillis();
        User user = User.builder().userName("test-" + time).age((int) time % 50).gender(time % 2 == 0 ? "F" : "M").build();
        return service.save(user);
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @GetMapping("/getByUserName/{userName}")
    public User get(@PathVariable("userName") String userName) {
        return service.findByUserName(userName);
    }


    @GetMapping("/findAll")
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/findByAge/{age}")
    public List<User> findByAge(@PathVariable("age") Integer age) {
        return service.findByAge(age);
    }

    @GetMapping("/findByGender/{gender}")
    public List<User> findByGender(@PathVariable("gender") String gender) {
        return service.findByGender(gender);
    }

    @GetMapping("/rest")
    public List<User> rest(){
        String url = "https://localhost:8080/app/user/findAll";
        ResponseEntity<List> result = restTemplate.getForEntity(url, List.class);
        return result.getBody();
    }
}
