package olutopas.command;

import olutopas.IO;
import olutopas.model.Rating;
import olutopas.repository.Datamapper;

public class MyRatingsKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public MyRatingsKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.println("Ratings by " + mapper.getCurrentUser().getName());
        for (Rating rating : mapper.getCurrentUser().getRatings()) {
            io.println(rating.toString());
        }
    }

    @Override
    public String komennonNimi() {
        return "show my ratings";
    }
}
