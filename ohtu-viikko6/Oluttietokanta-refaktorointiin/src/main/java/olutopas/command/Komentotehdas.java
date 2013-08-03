package olutopas.command;

import java.util.LinkedHashMap;
import java.util.Map;
import olutopas.IO;
import olutopas.repository.Datamapper;

public class Komentotehdas {

    private LinkedHashMap<String, Komento> komennot;
    private IO io;
    Datamapper mapper;

    public Komentotehdas(Datamapper mapper, IO io) {
        this.mapper = mapper;
        this.io = io;
        komennot = new LinkedHashMap<String, Komento>();
        komennot.put("login", new LoginKomento(mapper, io));
        komennot.put("1", new FindBreweryKomento(mapper, io));
        komennot.put("2", new FindBeerKomento(mapper, io));
        komennot.put("3", new AddBeerKomento(mapper, io));
        komennot.put("4", new ListBreweriesKomento(mapper, io));
        komennot.put("5", new DeleteBeerKomento(mapper, io));
        komennot.put("6", new ListBeersKomento(mapper, io));
        komennot.put("7", new DeleteBreweryKomento(mapper, io));
        komennot.put("8", new AddBreweryKomento(mapper, io));
        komennot.put("9", new MyRatingsKomento(mapper, io));
        komennot.put("0", new ListUsersKomento(mapper, io));
        komennot.put("q", new QuitKomento(io));
    }

    public Komento hae(String operaatio) {
        Komento komento = komennot.get(operaatio);
        if (komento == null) {
            komento = new UnknownKomento(mapper, io);
        }
        return komento;
    }

    public void menu() {
        io.println("");
        for (Map.Entry<String, Komento> entry : komennot.entrySet()) {
            String avain = entry.getKey();
            if (avain.equals("login")) {
                continue;
            }
            Komento komento = entry.getValue();
            io.println(avain + "  " + komento.komennonNimi());
        }
        io.println("");
    }
}
