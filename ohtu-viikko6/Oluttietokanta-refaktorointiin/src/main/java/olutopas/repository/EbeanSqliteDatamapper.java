/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.repository;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import java.util.List;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.User;

public class EbeanSqliteDatamapper implements Datamapper {

    private Class[] luokat;
    private EbeanServer server;
    private String tietokantaUrl;
    private boolean dropAndCreate;
    private User user;

    public EbeanSqliteDatamapper(String tietokantaUrl, boolean dropAndCreate, Class... luokat) {
        this.luokat = luokat;
        this.dropAndCreate = dropAndCreate;
        this.tietokantaUrl = tietokantaUrl;
        init();
    }

    private void init() {
        ServerConfig config = new ServerConfig();
        config.setName("beerDb");

        DataSourceConfig sqLite = new DataSourceConfig();
        sqLite.setDriver("org.sqlite.JDBC");
        sqLite.setUsername("mluukkai");
        sqLite.setPassword("mluukkai");
        sqLite.setUrl(tietokantaUrl);
        config.setDataSourceConfig(sqLite);
        config.setDatabasePlatform(new SQLitePlatform());
        config.getDataSourceConfig().setIsolationLevel(Transaction.READ_UNCOMMITTED);

        config.setDefaultServer(false);
        config.setRegister(false);

        for (Class luokka : luokat) {
            config.addClass(luokka);
        }

        if (dropAndCreate) {
            config.setDdlGenerate(true);
            config.setDdlRun(true);
            //config.setDebugSql(true);
        }

        server = EbeanServerFactory.create(config);

        if (dropAndCreate) {
            seedDatabase();
        }
    }

    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));

        server.save(new User("mluukkai"));
    }

    @Override
    public Brewery brewerywithName(String name) {
        return server.find(Brewery.class).where().like("name", name).findUnique();
    }

    @Override
    public User userWithName(String name) {
        return server.find(User.class).where().like("name", name).findUnique();
    }

    @Override
    public User getCurrentUser() {
        return user;
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }

    @Override
    public Beer beerWithName(String name) {
        return server.find(Beer.class).where().like("name", name).findUnique();
    }

    @Override
    public List<Beer> allBeers() {
        return server.find(Beer.class).orderBy("brewery.name").findList();
    }

    @Override
    public void save(Object object) {
        server.save(object);
    }

    @Override
    public void delete(Object object) {
        server.delete(object);
    }

    @Override
    public List<User> allUsers() {
        return server.find(User.class).findList();
    }

    @Override
    public List<Brewery> allBreweries() {
        return server.find(Brewery.class).findList();
    }
}
