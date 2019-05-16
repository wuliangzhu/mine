package com.mye.mine.controller;

import com.mye.mine.entity.User;
import com.mye.mine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * http://localhost:8080/swagger-ui.html
 * 可以看到你的所有接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "getInfo")
    public User getUserInfo(@RequestParam(value = "userId") int id) {
        User ret = this.userService.getUserById(id);

        return ret;
    }

    @PostMapping(value = "addUser")
    public int addUser(@RequestBody String name) {
        User user = new User();
        user.setUsername(name);

        return this.userService.insertUser(user);
    }
}
