package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Janis Suonperä
 * js428258@student.uta.fi
 * Oope-harkkatyö
 */
public class Video extends Tiedosto{
    /**
     * Attribuutit
     */
    private double pituus;
    
    /**
     * Rakentajat
     * @param nimi Videon nimi
     * @param koko Videon koko
     * @param pituus Videon pituus
     */
    public Video(String nimi, int koko, double pituus) {
        super(nimi, koko);
        asetaPituus(pituus);
    }
    
    /**
     * Aksessorit
     */
    /**
     * pituus-attribuutin setteri
     * @param pituus Videon pituus
     * @throws IllegalArgumentException jos pituus on pienempi kuin yksi.
     */
    @Setteri
    public void asetaPituus(double pituus)throws IllegalArgumentException{
        if(pituus>0)
            this.pituus = pituus;
        else
            throw new IllegalArgumentException();
    }
    
    /**
     * pituus-attribuutin getteri
     * @return Palauttaa videon pituuden.
     */
    @Getteri
    public double haePituus(){
        return pituus;
    }
    
    /**
     * Korvataan Object-luokan toString-metodi ja kutsutaan yliluokan korvattua versiota.
     * @return Videon nimi, koko ja pituus
     */
    @Override
    public String toString(){
        return super.toString() + " " + pituus + " s";
    }
}    

