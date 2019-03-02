package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Janis
 */
public abstract class Tiedosto {
    /**
     * Attribuutit
     */
    private String nimi;
    private int koko;
    
    /**
     * Rakentajat
     * @param nimi Tiedoston nimi
     * @param koko Tiedoston Koko
     */
    public Tiedosto(String nimi, int koko){
        asetaNimi(nimi);
        asetaKoko(koko);
    }
    /**
     * Aksessorit
     */
    
    /**
     * nimi-attribuutin setteri
     * @param nimi Tiedoston nimi
     * @throws IllegalArgumentException jos koko on pienempi kuin yksi.
     */
    @Setteri
    public void asetaNimi(String nimi) throws IllegalArgumentException{
        if(nimi!=null && nimi.length()>0)
            this.nimi = nimi;
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * nimi-attribuutin getteri
     * @return Palauttaa tiedoston nimen.
     */
    @Getteri
    public String haeNimi(){
        return nimi;
    }
    
    /**
     * koko-attribuutin setteri
     * @param koko Palauttaa tiedoston koon
     * @throws IllegalArgumentException jos koko on pienempi kuin yksi.
     */
    @Setteri
    public void asetaKoko(int koko) throws IllegalArgumentException{
        if(koko>0)
            this.koko = koko;
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * koko-attribuutin getteri
     * @return Palauttaa tiedoston koon.
     */
    @Getteri
    public int haeKoko(){
        return koko;
    }
    
    /**
     * Korvataan Object-luokan toString-metodi.
     * @return Teidoston nimi ja koko.
     */
    @Override
    public String toString(){
        return nimi + " " + koko + " B";
    }
}
