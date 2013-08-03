package olutopas.command;

import olutopas.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.repository.Datamapper;

public class AddBeerKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public AddBeerKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.print("to which brewery: ");
        String name = io.nextLine();
        Brewery brewery = mapper.brewerywithName(name);

        if (brewery == null) {
            io.println(name + " does not exist");
            return;
        }

        io.print("beer to add: ");

        name = io.nextLine();

        Beer exists = mapper.beerWithName(name);
        if (exists != null) {
            io.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        mapper.save(brewery);
        io.println(name + " added to " + brewery.getName());
    }

    @Override
    public String komennonNimi() {
        return "add beer";
    }
}
