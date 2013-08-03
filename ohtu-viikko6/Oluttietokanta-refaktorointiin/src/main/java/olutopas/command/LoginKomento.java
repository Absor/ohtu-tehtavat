package olutopas.command;

import olutopas.IO;
import olutopas.model.User;
import olutopas.repository.Datamapper;

public class LoginKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public LoginKomento(Datamapper mapper, IO io) {
        this.mapper = mapper;
        this.io = io;
    }

    @Override
    public void suorita() {
        while (true) {
            io.println("\nLogin (give ? to register a new user)\n");

            io.print("username: ");
            String name = io.nextLine();

            if (name.equals("?")) {
                registerUser();
                continue;
            }

            User user = mapper.userWithName(name);

            if (user != null) {
                mapper.setCurrentUser(user);
                break;
            }
            io.println("unknown user");
        }
    }

    private void registerUser() {
        io.println("Register a new user");
        io.print("give username: ");
        String name = io.nextLine();
        User u = mapper.userWithName(name);
        if (u != null) {
            io.println("user already exists!");
            return;
        }
        mapper.save(new User(name));
        io.println("user created!\n");
    }

    @Override
    public String komennonNimi() {
        return "login";
    }
}
