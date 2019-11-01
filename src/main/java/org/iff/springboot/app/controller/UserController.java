/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.iff.springboot.app.common.BaseController;
import org.iff.springboot.app.common.ResultBean;
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
public class UserController extends BaseController {
    @Autowired
    UserService service;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/add")
    public ResultBean add() {
        long time = System.currentTimeMillis();
        User user = User.builder().userName("test-" + time).age((int) time % 50).gender(time % 2 == 0 ? "F" : "M").build();
        return success(service.save(user));
    }

    @GetMapping("/get/{id}")
    public ResultBean get(@PathVariable("id") Long id) {
        return success(service.get(id));
    }

    @GetMapping("/getByUserName/{userName}")
    public ResultBean get(@PathVariable("userName") String userName) {
        return success(service.findByUserName(userName));
    }


    @GetMapping("/findAll")
    public ResultBean findAll() {
        return success(service.findAll());
    }

    @GetMapping("/findByAge/{age}")
    public ResultBean findByAge(@PathVariable("age") Integer age) {
        return success(service.findByAge(age));
    }

    @GetMapping("/findByGender/{gender}")
    public ResultBean findByGender(@PathVariable("gender") String gender) {
        return success(service.findByGender(gender));
    }

    @GetMapping("/rest")
    public ResultBean rest() {
        String url = "https://localhost:8080/app/user/findAll";
        ResponseEntity<ResultBean> result = restTemplate.getForEntity(url, ResultBean.class);
        return success(result.getBody());
    }
}
