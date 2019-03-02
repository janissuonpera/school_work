package oope2018ht.tiedostonlukija;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import oope2018ht.tiedostot.Kuva;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.tiedostot.Video;

/**
 *
 * @author Janis
 */
public class TiedostonLukija {

    public Tiedosto haeTiedosto(String teksti) throws FileNotFoundException, IOException {
        File file = new File(teksti);
        BufferedReader lukija = new BufferedReader(new FileReader(file));
        String[] tiedostonOsat = lukija.readLine().split(" ");

        if (tiedostonOsat[0].equals("Kuva")) {
            int koko = Integer.parseInt(tiedostonOsat[1]);
            int leveys = Integer.parseInt(tiedostonOsat[2]);
            int korkeus = Integer.parseInt(tiedostonOsat[3]);
            Kuva kuva = new Kuva(teksti, koko, leveys, korkeus);
            lukija.close();
            return kuva;

        } else if (tiedostonOsat[0].equals("Video")) {
            int koko = Integer.parseInt(tiedostonOsat[1]);
            double pituus = Double.parseDouble(tiedostonOsat[2]);
            Video video = new Video(teksti, koko, pituus);
            lukija.close();
            return video;
        } else {
            lukija.close();
            return null;
        }

    }
}
