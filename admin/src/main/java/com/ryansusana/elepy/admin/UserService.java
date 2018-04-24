package com.ryansusana.elepy.admin;

import java.util.Optional;

public class UserService {

    private final UserDao userDao;

    @java.beans.ConstructorProperties({"userDao"})
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public Optional<User> login(String usernameOrEmail, String password) {
        Optional<User> user = userDao.getByUsernameOrEmail(usernameOrEmail);

        if (user.isPresent()) {
            if (BCrypt.checkpw(password, user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }

}
