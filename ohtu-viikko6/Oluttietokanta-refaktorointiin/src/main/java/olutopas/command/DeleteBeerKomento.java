package olutopas.command;

import olutopas.IO;
import olutopas.model.Beer;
import olutopas.repository.Datamapper;

public class DeleteBeerKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public DeleteBeerKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.print("beer to delete: ");
        String name = io.nextLine();
        Beer beerToDelete = mapper.beerWithName(name);

        if (beerToDelete == null) {
            io.println(name + " not found");
            return;
        }

        mapper.delete(beerToDelete);
        io.println("deleted: " + beerToDelete);
    }

    @Override
    public String komennonNimi() {
        return "delete beer";
    }
}
