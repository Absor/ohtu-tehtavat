package olutopas.command;

import olutopas.IO;
import olutopas.model.Beer;
import olutopas.model.Rating;
import olutopas.repository.Datamapper;

public class FindBeerKomento implements Komento {

    private Datamapper mapper;
    private IO io;

    public FindBeerKomento(Datamapper mapper, IO io) {
        this.io = io;
        this.mapper = mapper;
    }

    @Override
    public void suorita() {
        io.print("beer to find: ");
        String name = io.nextLine();
        Beer foundBeer = mapper.beerWithName(name);

        if (foundBeer == null) {
            io.println(name + " not found");
            return;
        }

        io.println(foundBeer.toString());

        if (foundBeer.getRatings() != null && !foundBeer.getRatings().isEmpty()) {
            io.println("  number of ratings: " + foundBeer.getRatings().size() + " average " + foundBeer.averageRating());
        } else {
            io.println("no ratings");
        }

        io.print("give rating (leave emtpy if not): ");
        try {
            int rating = Integer.parseInt(io.nextLine());
            addRating(foundBeer, rating);
        } catch (Exception e) {
        }
    }

    private void addRating(Beer foundBeer, int value) {
        Rating rating = new Rating(foundBeer, mapper.getCurrentUser(), value);
        mapper.save(rating);
    }

    @Override
    public String komennonNimi() {
        return "find/rate beer";
    }
}
