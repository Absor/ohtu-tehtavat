package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(@Qualifier(value = "fileUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        if (password.length() < 8 || username.length() < 3) {
            return true;
        }
        
        boolean hasSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            // not allowing ; in password because of file parsing
            if (password.charAt(i) == ';') {
                return true;
            }
            if (!Character.isLetter(password.charAt(i))) {
                hasSpecial = true;
            }
        }
        if (!hasSpecial) {
            return true;
        }
        
        for (int i = 0; i < username.length(); i++) {
            // not allowing ; in usernames because of file parsing
            if (username.charAt(i) == ';') {
                return true;
            }
        }

        return false;
    }
}
