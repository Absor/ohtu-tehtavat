package olutopas.command;

import olutopas.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.repository.Datamapper;

public class FindBreweryKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public FindBreweryKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.print("brewery to find: ");
        String name = io.nextLine();
        Brewery foundBrewery = mapper.brewerywithName(name);

        if (foundBrewery == null) {
            io.println(name + " not found");
            return;
        }

        io.println(foundBrewery.toString());
        for (Beer bier : foundBrewery.getBeers()) {
            io.println("   " + bier.getName());
        }
    }

    @Override
    public String komennonNimi() {
        return "find brewery";
    }
}
