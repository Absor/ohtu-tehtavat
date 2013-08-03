package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner lukija = new Scanner(System.in);
        System.out.print("Anna opiskelijanumero: ");
        String url = "http://ohtustats-2013.herokuapp.com/opiskelija/" +lukija.nextLine() + ".json";
        
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
        
        InputStream stream = method.getResponseBodyAsStream();
        
        String bodyText = IOUtils.toString(stream);
        
        Gson mapper = new Gson();
        Palautukset palautukset = mapper.fromJson(bodyText, Palautukset.class);
        
        int tunnit = 0, tehtavat = 0;
        for (Palautus palautus : palautukset.getPalautukset()) {
            tunnit += palautus.getTunteja();
            tehtavat += palautus.getTehtavia();
            System.out.format("viikko %d: %2d tehtävää %-40s aikaa kului %2d tuntia\n", palautus.getViikko(), palautus.getTehtavia(), palautus.getTehtavat(), palautus.getTunteja());
        }
        System.out.format("\nyhteensä %2d tehtävää %2d tuntia\n", tehtavat, tunnit);
    }
}
