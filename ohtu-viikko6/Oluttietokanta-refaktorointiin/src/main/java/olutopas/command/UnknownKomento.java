package olutopas.command;

import olutopas.IO;
import olutopas.repository.Datamapper;

public class UnknownKomento implements Komento {

    Datamapper mapper;
    IO io;

    public UnknownKomento(Datamapper mapper, IO io) {
        this.mapper = mapper;
        this.io = io;
    }

    @Override
    public void suorita() {
        io.println("unknown command");
    }

    @Override
    public String komennonNimi() {
        return "unknown";
    }
}
