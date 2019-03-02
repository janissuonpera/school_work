package oope2018ht.viestit;

import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.omalista.OmaLista;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Komennettava;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Janis Suonperä
 */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    /**
     * Attribuutit
     */
    private int tunniste;
    private String teksti;
    private Viesti vastattu;
    private Tiedosto liite;
    private OmaLista vastaukset;
    
    /**
     * Rakentaja
     * @param tunniste Viestin tunniste, oltava suurempi kuin 0.
     * @param teksti Viestin teksti, oltava ainakin yhden merkin pituinen.
     * @param vastattu Viite vastattuun viestiin, voi olla null.
     * @param liite Viestiin liitetty tiedosto, voi olla null.
     */
    public Viesti(int tunniste, String teksti, Viesti vastattu, Tiedosto liite){
        asetaTunniste(tunniste);
        asetaTeksti(teksti);
        asetaVastattu(vastattu);
        asetaLiite(liite);
        asetaVastaukset(new OmaLista());

    }
    
    /*
     * Aksessorit
     */
    
    /**
     * Tunniste-attribuutin setteri
     * @param tunniste Viestin tunniste, oltava suurempi kuin 0.
     * @throws IllegalArgumentException jos virheellinen parametri.
     */
    @Setteri
    public void asetaTunniste(int tunniste)throws IllegalArgumentException{
        if(tunniste>0){
            this.tunniste = tunniste;
        }
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Tunniste-attribuutin getteri
     * @return Palauttaa tunnisteen.
     */
    @Getteri
    public int haeTunniste(){
        return tunniste;
    }
    
    /**
     * Teksti-attribuutin setteri
     * @param teksti Käyttäjän teksti viestissä, string-tyyppinen.
     * @throws IllegalArgumentException jos virheellinen parametri.
     */
    @Setteri
    public void asetaTeksti(String teksti)throws IllegalArgumentException{
        if(teksti!=null && teksti.length()>0){
            this.teksti = teksti;
        }
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Teksti-attribuutin getteri
     * @return Palauttaa tekstin.
     */
    @Getteri
    public String haeTeksti(){
        return teksti;
    }
    
    /**
     * Vastattu-attribuutin setteri, voi olla null.
     * @param vastattu Viite viestiin, johon tämä viesti on vastaus. Voi olla null.
     */
    @Setteri
    public void asetaVastattu(Viesti vastattu){
        this.vastattu = vastattu;
        if(vastattu!=null){
            this.vastattu.vastaukset.lisaaLoppuun(this);
        }
    }
    
    /**
     * Vastattu-attribuutin getteri.
     * @return Palauttaa vastattu-attribuutin.
     */
    @Getteri
    public Viesti haeVastattu(){
        return vastattu;
    }

    /**
     * Liite-attribuutin setteri, voi olla null.
     * @param liite Viite viestiin liitettyyn tiedostoon.
     */
    @Setteri
    public void asetaLiite(Tiedosto liite){
        this.liite = liite;
    }

    /**
     * Liite-attribuutin getteri
     * @return Palauttaa liitteen.
     */
    @Getteri
    public Tiedosto haeLiite(){
        return liite;
    }

    /**
     * Vastaukset-attribuutin setteri, ei voi olla null.
     * @param vastaukset Viitteet viestiin vastanneisiin viesteihin.
     * @throws IllegalArgumentException jos virheellinen parametri.
     */
    @Setteri
    public void asetaVastaukset(OmaLista vastaukset)throws IllegalArgumentException{
        if(vastaukset!=null){
            this.vastaukset = vastaukset;
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Vastaukset-attribuutin getteri.
     * @return Palauttaa vastaukset.
     */
    @Getteri
    public OmaLista haeVastaukset(){
        return vastaukset;
    }

    /**
     * Toteutetaan Comparable-rajapinnan ainoa metodi.
     * @param t Parametriksi saatu viesti-olio.
     * @return palauttaa -1, 0 tai 1.
     */
    @Override
    public int compareTo(Viesti t) {
        if(tunniste<t.haeTunniste())
            return -1;
        else if(tunniste==t.haeTunniste())
            return 0;
        else
            return 1;
    }

    /**
     * Korvataa Object-luokan equals-metodi. Vertaillaan, jos viesti-olioiden tunnisteet ovat samat.
     * @param obj Liitetään olioon viesti-luokan viite.
     * @return Palauttaa true, jos tunnisteet samat. Muutoin palauttaa falsen.
     */
    @Override
    public boolean equals(Object obj){
      try {
         // Asetetaan olioon Viesti-luokan viite,
         // jotta voidaan kutsua Viesti-luokan haeTunniste-getteriä..
         Viesti v = (Viesti)obj;

         // Oliot ovat samat, jos tunnisteiden arvot ovat samat.
         return (tunniste == v.haeTunniste());
      }
      catch (Exception e) {
         return false;
      }
    }

    /**
     * Korvataan Object-luokan toString-metodi.
     * @return Palauttaa viestin tunnisteen.
     */
    @Override
    public String toString(){
        if(liite!=null)
            return "#" + tunniste + " " + teksti + " (" + liite + ")";
        else
            return "#" + tunniste + " " + teksti;
    }

    /**
     * Hakee parametriksi annettua viestiä listasta, johon on säilötty kaikki viitteet
     * viestiin vastaaviin viesteihin.
     * @param haettava viite haettavaan viestiin.
     * @return viite löydettyyn viestiin. Palauttaa null, jos viestiä ei löydy.
     * @throws IllegalArgumentException jos parametri on null.
     */
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if(haettava!=null){
            Viesti haku = (Viesti) vastaukset.hae(haettava);
            if(haku!=null)
                return haku;
            else
                return null;
        }
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * Lisää viestin listalle, johon on säilötty kaikki viitteet viestiin vastaaviin
     * veisteihin.
     * @param lisattava Viesti, joka halutaan lisätä listalle.
     * @throws IllegalArgumentException jos parametri on null tai viesti löytyy jo
     * listalta.
     */
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if(lisattava!=null && hae(lisattava)==null){
            vastaukset.lisaaLoppuun(lisattava);
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Tyhjentää viestin tekstin ja korvaa sen vakiolla. Jos viestiin on liitetty tiedosto,
     * se korvataan null arvolla
     */
    @Override
    public void tyhjenna() {
        if(teksti!=null){
            teksti = POISTETTUTEKSTI;
            if(liite!=null)
                liite = null;
        }
    }

}
