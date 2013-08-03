package olutopas.command;

import olutopas.IO;
import olutopas.model.Brewery;
import olutopas.repository.Datamapper;

public class AddBreweryKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public AddBreweryKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.print("brewery to add: ");
        String name = io.nextLine();
        Brewery brewery = mapper.brewerywithName(name);

        if (brewery != null) {
            io.println(name + " already exists!");
            return;
        }

        mapper.save(new Brewery(name));
    }

    @Override
    public String komennonNimi() {
        return "add brewery";
    }
}
