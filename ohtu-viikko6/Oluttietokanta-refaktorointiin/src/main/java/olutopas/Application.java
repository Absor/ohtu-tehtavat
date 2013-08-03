package olutopas;

import olutopas.command.Komento;
import olutopas.command.Komentotehdas;

import olutopas.repository.Datamapper;

public class Application {

    private IO io;
    private Komentotehdas komennot;
    private Datamapper mapper;

    public Application(Datamapper mapper, IO io) {
        this.komennot = new Komentotehdas(mapper, io);
        this.io = io;
        this.mapper = mapper;
    }

    public void run() {
        komennot.hae("login").suorita();

        io.println("\nWelcome to Ratebeer " + mapper.getCurrentUser().getName());

        while (true) {
            komennot.menu();
            io.print("> ");
            String command = io.nextLine();

            Komento komento = komennot.hae(command);
            komento.suorita();

            io.print("\npress enter to continue");
            io.nextLine();
        }
    }
}
