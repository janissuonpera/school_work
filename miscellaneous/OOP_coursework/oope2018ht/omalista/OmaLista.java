/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.omalista;

import fi.uta.csjola.oope.lista.LinkitettyLista;
import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.apulaiset.Ooperoiva;

/**
 *
 * @author Janis
 */
public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista>{
    
    /**
     * Tutkii onko listalla haettavaa vastaavaa alkiota ja palauttaa viitteen siihen.
     * @param haettava olio, jota vastaavaa alkiota haetaan. 
     * @return viite löydettyyn alkioon. Palauttaa null, jos parametri on null,
     * alkioita ei löydy tai lista on tyhjä.
     */
    @Override
    public Object hae(Object haettava) {
        if(haettava==null || onkoTyhja())
            return null;
        else{
            int i=0;
            boolean lippu = true;
            Object haunTulos = haettava;
            //Silmukka katkeaa, jos päästään listan loppuun
            //tai lippuu käännetään.
            while(i<koko && lippu){
                if(alkio(i).equals(haettava)){
                    haunTulos = alkio(i);
                    //Lippu k??nnet??n.
                    lippu = false;
                }   
                i++;
            }
            //Jos lippu on käännetty, palautetaan haun tulos, muuten palautetaan null.
            if(!lippu)
                return haunTulos;
            else
                return null;
        }
    }

    
    @Override
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object uusi) {
        if(uusi==null)
            return false;
        else{
            try{
                Comparable vertailtava = (Comparable) uusi;
                if(onkoTyhja())
                    lisaaAlkuun(uusi);
                else{
                    boolean lippu = true;
                    int i = 0;
                    Solmu nykyinen = paa();
                    while(i<koko() && lippu){
                        if(vertailtava.compareTo(alkio(i))>0 && nykyinen.seuraava()==null){
                            lisaaLoppuun(uusi);
                            lippu = false;
                        }
                        else if(vertailtava.compareTo(alkio(i))<0){
                            lisaa(i, uusi);
                            lippu = false;
                        }
                        else if(nykyinen.seuraava()==null){
                            lisaaLoppuun(uusi);
                            lippu = false;
                        }
                        nykyinen = nykyinen.seuraava();
                        i++;
                    }
                }
                return true;
            }catch(Exception e){
                return false;
            }
        }
    }
    
    /**
     * Luo ja palauttaa uuden listan, jossa alkuperäisen listan n ensimmäistä alkiota.
     * @param n uudelle listalle lisättävien alkioiden määrä.
     * @return viite uuteen listaan.
     */
    @Override
    public OmaLista annaAlku(int n) {
        if(onkoTyhja() || n<1 || n>koko()){
            return null;
        }
        else{
            OmaLista alku = new OmaLista();
            for(int i=0; i<n; i++){
                alku.lisaaLoppuun(alkio(i));
            }
            return alku;
        }
    }
    
    /**
     * Luo ja palauttaa uuden listan, jossa alkuperäisen listan n viimeistä alkiota.
     * @param n uudelle listallae lisättävien alkioiden määrä.
     * @return viite uuteen 
     */
    @Override
    public OmaLista annaLoppu(int n) {
        if(onkoTyhja() || n<1 || n>koko()){
            return null;
        }
        else{
            OmaLista loppu = new OmaLista();
            for(int i=0; i<n; i++){
                loppu.lisaaLoppuun(alkio(koko()-n+i));
            }
            return loppu;
        }
    }
    
}
