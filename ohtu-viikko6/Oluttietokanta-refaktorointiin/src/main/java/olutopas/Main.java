package olutopas;

import olutopas.model.Beer;
import olutopas.model.Brewery;

import olutopas.model.Rating;
import olutopas.model.User;
import olutopas.repository.Datamapper;
import olutopas.repository.EbeanSqliteDatamapper;

public class Main {

    public static void main(String[] args) {
        boolean dropAndCreateTables = true;
        Datamapper mapper = new EbeanSqliteDatamapper("jdbc:sqlite:beer.db", dropAndCreateTables, Beer.class, Brewery.class, User.class, Rating.class);
        IO io = new ScannerIO();
        new Application(mapper, io).run();
    }
}
