/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.app.service;

import org.iff.springboot.app.dao.UserDao;
import org.iff.springboot.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Service
public class UserService {
    @Autowired
    UserDao dao;

    public List<User> findAll() {
        return dao.findAll();
    }

    public User get(Long id) {
        return dao.findById(id).get();
    }

    public User findByUserName(String userName) {
        return dao.findByUserName(userName);
    }

    public List<User> findByAge(Integer age) {
        return dao.findByAgeOrderByUserNameAsc(age);
    }

    public List<User> findByGender(String gender) {
        return dao.findByGenderOrderByUserNameAsc(gender);
    }

    public User save(User user) {
        return dao.save(user);
    }

}
