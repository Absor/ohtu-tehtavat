package olutopas.command;

import olutopas.IO;
import olutopas.model.User;
import olutopas.repository.Datamapper;

public class ListUsersKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public ListUsersKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        for (User user : mapper.allUsers()) {
            io.println(user.getName() + " " + user.getRatings().size() + " ratings");
        }
    }

    @Override
    public String komennonNimi() {
        return "list users";
    }
}
