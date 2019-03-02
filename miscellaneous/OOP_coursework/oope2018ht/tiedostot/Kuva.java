package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Janis
 */
public class Kuva extends Tiedosto{
    /*
     * Attribuutit
     */
    private int leveys;
    private int korkeus;
    
    /**
     * Rakentajat
     * @param nimi Kuvan nimi
     * @param koko Kuvan koko
     * @param leveys Kuvan leveys
     * @param korkeus Kuvan korkeus
     */
    public Kuva(String nimi, int koko, int leveys, int korkeus) {
        super(nimi, koko);
        asetaLeveys(leveys);
        asetaKorkeus(korkeus);
    }
    
    /*
     * Aksessorit
     */
    /**
     * leveys-attribuutin setteri
     * @param leveys Kuvan leveys
     * @throws IllegalArgumentException jos koko on pienempi kuin yksi.
     */
    @Setteri
    public void asetaLeveys(int leveys)throws IllegalArgumentException{
        if(leveys>0)
            this.leveys = leveys;
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * leveys-attribuutin getteri
     * @return Palauttaa kuvan leveyden.
     */
    @Getteri
    public int haeLeveys(){
        return leveys;
    }
    
    /**
     * korkeus-attribuutin setteri
     * @param korkeus Kuvan korkeus.
     * @throws IllegalArgumentException jos kuvan korkeus on pienempi kuin yksi.
     */
    @Setteri
    public void asetaKorkeus(int korkeus)throws IllegalArgumentException{
        if(korkeus>0)
            this.korkeus = korkeus;
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * korkeus-attribuutin getteri
     * @return Palauttaa kuvan korkeuden.
     */
    @Getteri
    public int haeKorkeus(){
        return korkeus;
    }
    
    /**
     * Korvataan Object-luokan toString-metodi ja kutsutaan yliluokan korvattua versiota.
     * @return Kuvan nimi, koko, leveys ja korkeus.
     */
    @Override
    public String toString(){
        return super.toString() + " " + leveys + "x" + korkeus;
    }
}
