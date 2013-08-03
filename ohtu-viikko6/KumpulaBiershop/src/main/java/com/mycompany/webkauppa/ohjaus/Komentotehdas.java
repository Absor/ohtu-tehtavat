package com.mycompany.webkauppa.ohjaus;

import com.mycompany.webkauppa.sovelluslogiikka.Ostoskori;

public class Komentotehdas {

    public Komento ostoksenLisaysKoriin(Ostoskori ostoskori, long tuoteId) {
        return new OstoksenLisaysKoriin(ostoskori, tuoteId);
    }

    public Komento ostoksenSuoritus(String nimi, String osoite, String luottokorttinumero, Ostoskori ostoskori) {
        return new OstoksenSuoritus(nimi, osoite, luottokorttinumero, ostoskori);
    }

    public Komento ostoksenPoistoKorista(Ostoskori ostoskori, long tuoteId) {
        return new OstoksenPoistoKorista(ostoskori, tuoteId);
    }
}
