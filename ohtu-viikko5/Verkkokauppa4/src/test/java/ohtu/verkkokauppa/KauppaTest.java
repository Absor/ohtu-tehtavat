package ohtu.verkkokauppa;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    @Test
    public void yhdenOstoksenJalkeenPankinMetodiaTilisiirtoKutsutaanOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");
        
        // oikea asiakas, tilinumero, summa
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));
    }
    
    @Test
    public void kahdenEriOstoksenJalkeenPankinMetodiaTilisiirtoKutsutaanOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 15));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("mauri", "23456");
        
        // oikea asiakas, tilinumero, summa
        verify(pankki).tilisiirto(eq("mauri"), anyInt(), eq("23456"), anyString(), eq(20));
    }
    
    @Test
    public void kahdenSamanOstoksenJalkeenPankinMetodiaTilisiirtoKutsutaanOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("janne", "34567");
        
        // oikea asiakas, tilinumero, summa
        verify(pankki).tilisiirto(eq("janne"), anyInt(), eq("34567"), anyString(), eq(10));
    }
    
    @Test
    public void kahdenEriOstoksenJoistaToistaEiVarastossaJalkeenPankinMetodiaTilisiirtoKutsutaanOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 15));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu("matti", "56789");
        
        // oikea asiakas, tilinumero, summa
        verify(pankki).tilisiirto(eq("matti"), anyInt(), eq("56789"), anyString(), eq(5));
    }
    
    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksenTiedot() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("matti", "56789");
        
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(5));
    }
    
    @Test
    public void kauppaPyytaaUudenViitenumeronJokaiselleMaksutapahtumalle() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("janne", "12343");
        
        verify(viite, times(1)).uusi();
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("matti", "56789");
        
        verify(viite, times(2)).uusi();
    }
    
    @Test
    public void kahdenSamanOstoksenJaYhdenPoistonJalkeenPankinMetodiaTilisiirtoKutsutaanOikein() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(1);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        
        Kauppa kauppa = new Kauppa(varasto, pankki, viite);
        
        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(1);
        kauppa.tilimaksu("pertti", "98765");
        
        // oikea asiakas, tilinumero, summa
        verify(pankki).tilisiirto(eq("pertti"), anyInt(), eq("98765"), anyString(), eq(5));
    }
}