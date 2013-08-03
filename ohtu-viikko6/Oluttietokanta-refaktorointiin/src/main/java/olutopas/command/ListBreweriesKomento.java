package olutopas.command;

import java.util.List;
import olutopas.IO;
import olutopas.model.Brewery;
import olutopas.repository.Datamapper;

public class ListBreweriesKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public ListBreweriesKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        List<Brewery> breweries = mapper.allBreweries();
        for (Brewery brewery : breweries) {
            io.println(brewery.toString());
        }
    }

    @Override
    public String komennonNimi() {
        return "list breweries";
    }
}
