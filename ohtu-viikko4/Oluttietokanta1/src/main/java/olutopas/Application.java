package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);
    private User user = null;

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }

        while (user == null) {
            System.out.println("Login (give ? to register a new user)");
            System.out.print("username: ");
            String command = scanner.nextLine();
            System.out.println();

            if (command.equals("?")) {
                System.out.println("Register a new user");
                System.out.print("give username: ");
                String username = scanner.nextLine();
                User exists = server.find(User.class).where().like("username", command).findUnique();
                if (exists == null) {
                    server.save(new User(username));
                    System.out.println("user created!");
                } else {
                    System.out.println("Username already exists.");
                }
                System.out.println();
            } else {
                user = server.find(User.class).where().like("username", command).findUnique();
                if (user == null) {
                    System.out.println("Username not registered.");
                    System.out.println();
                }
            }
        }

        System.out.println("Welcome to Ratebeer " + user.getUsername());

        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findAndRateBeer();
            } else if (command.equals("3")) {
                addBrewery();
            } else if (command.equals("4")) {
                addBeer();
            } else if (command.equals("5")) {
                addPub();
            } else if (command.equals("6")) {
                addBeerToPub();
            } else if (command.equals("7")) {
                listBreweries();
            } else if (command.equals("8")) {
                listBeers();
            } else if (command.equals("9")) {
                listPubs();
            } else if (command.equals("w")) {
                deleteBrewery();
            } else if (command.equals("e")) {
                deleteBeer();
            } else if (command.equals("r")) {
                removeBeerFromPub();
            } else if (command.equals("t")) {
                showMyRatings();
            } else if (command.equals("y")) {
                listUsers();
            } else if (command.equals("u")) {
                showBeersInPub();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find/rate beer");
        System.out.println("3   add brewery");
        System.out.println("4   add beer");
        System.out.println("5   add pub");
        System.out.println("6   add beer to pub");
        System.out.println("7   list breweries");
        System.out.println("8   list beers");
        System.out.println("9   list pubs");
        System.out.println("w   delete brewery");
        System.out.println("e   delete beer");
        System.out.println("r   remove beer from pub");
        System.out.println("t   show my ratings");
        System.out.println("y   list users");
        System.out.println("u   show beers in pub");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
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
        server.save(new User("testi"));
    }

    private void findAndRateBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBeer);

        System.out.print("give rating (leave empty if not): ");
        String rating = scanner.nextLine();
        if (!rating.isEmpty()) {
            int value;
            try {
                value = Integer.parseInt(rating);
            } catch (Exception e) {
                System.out.println("Not an integer value.");
                return;
            }
            server.save(new Rating(foundBeer, user, value));
        }
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void listBeers() {
        List<Beer> beers = server.find(Beer.class).orderBy("brewery.id").findList();
        for (Beer beer : beers) {
            System.out.println(beer);
        }
    }

    private void listUsers() {
        List<User> users = server.find(User.class).findList();
        for (User print : users) {
            System.out.println(print);
        }
    }

    private void listPubs() {
        List<Pub> pubs = server.find(Pub.class).findList();
        for (Pub pub : pubs) {
            System.out.println(pub);
            for (Beer beer : pub.getBeers()) {
                System.out.println("  " + beer);
            }
        }
    }

    private void showBeersInPub() {
        System.out.print("which pub: ");
        String n = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", n).findUnique();

        if (pub == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(pub);
        for (Beer beer : pub.getBeers()) {
            System.out.println("  " + beer);
        }
    }

    private void addBrewery() {
        System.out.print("brewery to add: ");

        String name = scanner.nextLine();

        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(new Brewery(name));
        System.out.println(name + " added.");
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void addPub() {
        System.out.print("pub to add: ");

        String name = scanner.nextLine();

        Pub exists = server.find(Pub.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(new Pub(name));
    }

    private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }

        pub.addBeer(beer);
        server.save(pub);
    }

    private void deleteBrewery() {
        System.out.print("brewery to delete: ");
        String n = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", n).findUnique();

        if (breweryToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete);
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }

    private void removeBeerFromPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }

        pub.removeBeer(beer);
        server.save(pub);
    }

    private void showMyRatings() {
        server.refreshMany(user, "ratings");
        List<Rating> ratings = user.getRatings();
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
    }
}
