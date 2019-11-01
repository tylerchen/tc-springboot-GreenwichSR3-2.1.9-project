/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.app.dao;

import org.iff.springboot.app.common.BaseDao;
import org.iff.springboot.app.entity.User;

import java.util.List;

/**
 * UserDao
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
public interface UserDao extends BaseDao<User> {

    User findByUserName(String userName);

    List<User> findByAgeOrderByUserNameAsc(Integer age);

    List<User> findByGenderOrderByUserNameAsc(String gender);
}
