package com.evan.aiu.dao;


import com.evan.aiu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findById(int id);

    List<User> findByPhone(String phone);

    User getByUsernameAndPassword(String username,String password);
}
