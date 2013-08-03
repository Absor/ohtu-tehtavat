package olutopas.repository;

import java.util.List;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.User;

public interface Datamapper {

    Brewery brewerywithName(String name);

    User userWithName(String name);
    
    Beer beerWithName(String name);

    User getCurrentUser();

    void setCurrentUser(User user);
    
    void save(Object object);
    
    void delete(Object object);
    
    List<Beer> allBeers();
    
    List<User> allUsers();

    List<Brewery> allBreweries();
}
