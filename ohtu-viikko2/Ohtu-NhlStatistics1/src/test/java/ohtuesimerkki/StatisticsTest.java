package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsTest {

    Statistics stats;
    
    Reader readerStub = new Reader() {
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };
    
    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }
    
    @Test
    public void listattuPelaajaLoytyy() {
        Player player = stats.search("Semenko");
        
        assertEquals("Semenko", player.getName());
        assertEquals("EDM", player.getTeam());
    }
    
    @Test
    public void listaamatonPelaajaEiLoydy() {
        Player player = stats.search("Karri");
        
        assertNull(player);
    }
    
    @Test
    public void tiimiHakuPalauttaaKaikkiPelaajat() {
        List<Player> pelaajat = stats.team("EDM");
        
        assertEquals(3, pelaajat.size());
        assertEquals("Semenko", pelaajat.get(0).getName());
        assertEquals("Kurri", pelaajat.get(1).getName());
        assertEquals("Gretzky", pelaajat.get(2).getName());
    }
    
    @Test
    public void tiimiHakuPalauttaaTyhjaaTuntemattomalla() {
        List<Player> pelaajat = stats.team("TES");
        
        assertEquals(0, pelaajat.size());
    }
    
    @Test
    public void parasPelaajaLoytyy() {
        List<Player> parhaat = stats.topScorers(0);
        
        assertEquals(1, parhaat.size());
        assertEquals("Gretzky", parhaat.get(0).getName());
    }
    
    @Test
    public void kaikkiListataanParhaisiin() {
        List<Player> parhaat = stats.topScorers(4);
        
        assertEquals(5, parhaat.size());
    }
    
    @Test
    public void parhaatListaaVainKaikkiMahdolliset() {
        List<Player> parhaat = stats.topScorers(10);
        
        assertEquals(5, parhaat.size());
    }
}