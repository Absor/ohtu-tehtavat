package olutopas.command;

import java.util.List;
import olutopas.IO;
import olutopas.model.Beer;
import olutopas.repository.Datamapper;

public class ListBeersKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public ListBeersKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        List<Beer> beers = mapper.allBeers();
        for (Beer beer : beers) {
            io.println(beer.toString());
            if (beer.getRatings() != null && !beer.getRatings().isEmpty()) {
                io.println("  ratings given " + beer.getRatings().size() + " average " + beer.averageRating());
            } else {
                io.println("  no ratings");
            }
        }
    }

    @Override
    public String komennonNimi() {
        return "list beers";
    }
}
