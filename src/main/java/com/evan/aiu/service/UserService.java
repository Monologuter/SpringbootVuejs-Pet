package com.evan.aiu.service;

import com.evan.aiu.dao.AdminRoleDAO;
import com.evan.aiu.dao.AdminUserRoleDAO;
import com.evan.aiu.dao.UserDAO;
import com.evan.aiu.dto.UserDTO;
import com.evan.aiu.entity.AdminRole;
import com.evan.aiu.entity.AdminUserRole;
import com.evan.aiu.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminUserRoleDAO adminUserRoleDAO;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    public List<UserDTO> list() {
        List<User> users = userDAO.findAll();
        List<AdminRole> roles;

        List<UserDTO> userDTOS = users.stream().map(user -> (UserDTO) new UserDTO().convertFrom(user)).collect(Collectors.toList());

        for (UserDTO userDTO : userDTOS) {
            roles = adminRoleService.listRolesByUser(userDTO.getUsername());
            userDTO.setRoles(roles);
        }

        return userDTOS;
    }

    public boolean isExist(String username) {
        User user = userDAO.findByUsername(username);
        return null != user;
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public List<User> findByRole () {
        List<User> userList = new ArrayList<>();
        List<AdminUserRole> byRid = adminUserRoleDAO.findByRid(10);
        for (AdminUserRole adminUserRole: byRid) {
            User user = userDAO.findById(adminUserRole.getUid());
            userList.add(user);
        }
        return userList;
    }

    public boolean volunteerAuti(String username) {
        User byUsername = userDAO.findByUsername(username);
        List<AdminUserRole> adminUserRoles = adminUserRoleService.listAllByUid(byUsername.getId());
        if (adminUserRoles.isEmpty()) {
            return false;
        }
        for (AdminUserRole adminUserRole : adminUserRoles) {
            if (adminUserRole.getRid() == 10) {
                return true;
            }
        }
        return false;
    }

    public User get(String username, String password) {
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public int register(User user) {
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if ("".equals(username) || "".equals(password)) {
            return 0;
        }

        boolean exist = isExist(username);

        if (exist) {
            return 2;
        }

        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        userDAO.save(user);

        return 1;
    }

    public void updateUserStatus(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setEnabled(user.isEnabled());
        userDAO.save(userInDB);
    }

    public void updatePassword(User user) {
        List<User> userList = userDAO.findByPhone(user.getPhone());
        if (userList.isEmpty()) {
            return;
        }
        User userInDB = userList.get(0);
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, times).toString();
        userInDB.setPassword(encodedPassword);
        userDAO.save(userInDB);
    }

    public void resetPassword(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        userInDB.setPassword(encodedPassword);
        userDAO.save(userInDB);
    }

    public void editUser(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        userDAO.save(userInDB);
        adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }

    public void editUsera(User user) {
        User userInDB = userDAO.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        userDAO.save(userInDB);
    }

    public void deleteById(int id) {
        userDAO.deleteById(id);
    }
}