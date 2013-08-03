package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivisenVarastonLuontiLuoTyhjan() {
        varasto = new Varasto(-0.1);
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaOikeaTilavuus2() {
        varasto = new Varasto(0.1);
        assertEquals(0.1, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisaaTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void liikaLisaysTayttaa() {
        varasto.lisaaVarastoon(12);

        // saldon pitäisi olla varaston maksimi
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenLisaysEiMuuta() {
        varasto.lisaaVarastoon(5);
        varasto.lisaaVarastoon(-1);
        varasto.lisaaVarastoon(-5);

        // saldon pitäisi olla alussa lisätty 5
        assertEquals(5, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void liikaOttoPalauttaaKaiken() {
        varasto.lisaaVarastoon(6);

        double saatuMaara = varasto.otaVarastosta(8);

        // pitäisi saada kaikki 6 varastosta
        assertEquals(6, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void liikaOttoTyhjentää() {
        varasto.lisaaVarastoon(6);

        varasto.otaVarastosta(8);

        // varaston pitäisi olla tyhjä
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenOttoEiMuuta() {
        varasto.lisaaVarastoon(5);
        varasto.otaVarastosta(-1);
        varasto.otaVarastosta(-5);

        // saldon pitäisi olla alussa lisätty 5
        assertEquals(5, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void merkkijonoesitysOikein() {
        varasto.lisaaVarastoon(6);

        // varaston merkkijonoesityksen pitäisi olla määrää vastaava
        assertEquals("saldo = 6.0, vielä tilaa 4.0", varasto.toString());
    }

    @Test
    public void negatiivinenAlkutilavuus() {
        varasto = new Varasto(-1);

        // negatiivinen tilavuus luo 0 tilavuuksisen
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void alkutilavuusNolla() {
        varasto = new Varasto(0);

        // tilavuus 0
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void saldoJaTilavuusYksi() {
        varasto = new Varasto(1, 1);

        // tilavuus ja saldo 1
        assertEquals(1, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(1, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void isompiAlkusaldo() {
        varasto = new Varasto(1, 2);

        // ylitäyttö täyttää vain maksimiin
        assertEquals(1, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(1, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenAlkutilavuusAlkusaldolla() {
        varasto = new Varasto(-1, 2);

        // negatiivinen on max 0 tilavuus, saldon pitäisi olla sama
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(-1, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenAlkutilavuusJaAlkusaldo() {
        varasto = new Varasto(-1, -1);

        // kummatkin nollaan negatiivisilla
        assertEquals(0, varasto.getTilavuus(), vertailuTarkkuus);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
}