package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

public class FileUserDao implements UserDao {
    
    String filename;
    private List<User> users;

    public FileUserDao(String filename) throws FileNotFoundException, IOException {
        this.filename = filename;
        users = new ArrayList<User>();
        
        File file = new File(filename);
        Scanner fileRead = new Scanner(file);
        while (fileRead.hasNext()) {
            String userString = fileRead.nextLine();
            String[] splitUserString = userString.split(";");
            User user = new User(splitUserString[0], splitUserString[1]);
            users.add(user);
        }
        fileRead.close();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        try {
            FileWriter fileWrite = new FileWriter(filename, true);
            fileWrite.append(user.getUsername() + ";" + user.getPassword() + "\n");
            fileWrite.close();
        } catch (IOException ex) {
            return;
        }
        users.add(user);
    }
}
