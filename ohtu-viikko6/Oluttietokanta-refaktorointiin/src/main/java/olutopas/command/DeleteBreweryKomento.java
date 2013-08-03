package olutopas.command;

import olutopas.IO;
import olutopas.model.Brewery;
import olutopas.repository.Datamapper;

public class DeleteBreweryKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public DeleteBreweryKomento(Datamapper mapper, IO io) {
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

        mapper.delete(brewery);

        io.println("deleted: " + name);
    }

    @Override
    public String komennonNimi() {
        return "delete brewery";
    }
}
