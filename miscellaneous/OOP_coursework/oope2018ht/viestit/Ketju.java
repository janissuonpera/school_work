package oope2018ht.viestit;

import java.io.IOException;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;
/**
 *
 * @author Janis
 */
public class Ketju{
    private int ketjunTunniste;
    private String aihe;
    private OmaLista viestit;
    
    public Ketju(String teksti, Tiedosto tiedosto, Alue keskustelualue) throws IllegalArgumentException, IOException{
        if(teksti.length()>0 && keskustelualue!=null){
            if(teksti.length()==1 && teksti.charAt(0)==' ')
                throw new IllegalArgumentException();
            else{
                if(tiedosto!=null)
                    aihe = teksti + " (" + tiedosto + ")";
                else
                    aihe = teksti;
                ketjunTunniste = keskustelualue.haeKeskustelut()+1;
                viestit = new OmaLista();
            } 
        }else
            throw new IllegalArgumentException();
    }
    
    public void lisaaViesti(Viesti viesti){
        viestit.lisaaLoppuun(viesti);
    }

    public OmaLista getViestit() {
        return viestit;
    }
    
    public OmaLista haeAloitukset(){
        OmaLista aloitukset = new OmaLista();
        for(int i=0; i<viestit.koko(); i++){
            Viesti haettu = (Viesti) viestit.alkio(i);
            if(haettu.haeVastattu()==null)
                aloitukset.lisaaLoppuun(viestit.alkio(i));
        }
        return aloitukset;
    }
    
    public void listaaVanhimmat(int x)throws IllegalArgumentException{
        if(x>0 && x<=viestit.koko()){
            OmaLista vanhimmatViestit = viestit.annaAlku(x);

            for(int i=0; i<vanhimmatViestit.koko(); i++){
                System.out.println(vanhimmatViestit.alkio(i));
            }
        }
        else
            throw new IllegalArgumentException();
    }    
    
    public void listaaUusimmat(int x)throws IllegalArgumentException{
        if(x>0 && x<=viestit.koko()){
            OmaLista uusimmatViestit = viestit.annaLoppu(x);

            for(int i=0; i<uusimmatViestit.koko(); i++){
                System.out.println(uusimmatViestit.alkio(i));
            }
        }
        else
            throw new IllegalArgumentException();        
    }
    
    public Viesti haeViesti(int tunniste){
        Viesti haettava = null;
        
        for(int i=0; i<viestit.koko();i++){
            Viesti viesti = (Viesti) viestit.alkio(i);
            if(viesti.haeTunniste() == tunniste){
                haettava = viesti;
            }
        }
        return haettava;
    }
    
    public void haeViesti(String teksti)throws IllegalArgumentException{
        if(teksti.length()>0){
            for(int i=0; i<viestit.koko(); i++){
                Viesti haettava = (Viesti) viestit.alkio(i);
                if(haettava.toString().contains(teksti)){
                    System.out.println(haettava.toString());
                }       
            }
        }
        else
            throw new IllegalArgumentException();
    }
    
    public int koko(){
        return viestit.koko();
    }
    
    public void tulostaListana(){
        System.out.println("=\n== " + this + "\n===");
        OmaLista varaketju = new OmaLista();
        for(int i=0; i<viestit.koko(); i++){
            varaketju.lisaa(viestit.alkio(i));
        }

        for(int i=0; i<varaketju.koko(); i++)
            System.out.println(varaketju.alkio(i));
    }
    
    public void tulostaPuuna(){
        System.out.println("=\n== " + this + "\n===");
        for(int i=0; i<viestit.koko(); i++){
            tulostaPuuna((Viesti) haeAloitukset().alkio(i), 0);
        }
    }
    
    public void tulostaPuuna(Viesti viesti, int syvyys){
        if(viesti!=null){
            for(int x=0; x<syvyys; x++){
                System.out.print("   ");
            }
            System.out.println(viesti);

            for(int i=0; i<viesti.haeVastaukset().koko(); i++){
                Viesti haettava = (Viesti) viesti.haeVastaukset().alkio(i);
                if(haettava.haeVastattu()==viesti)
                    tulostaPuuna(haettava, (syvyys+1));
                else
                    tulostaPuuna(haettava, (syvyys + i + 1));
            }
        }
    }
    
    public void tyhjenna(int x){
        Viesti tyhjennettava = haeViesti(x);
        if(tyhjennettava!=null){
            tyhjennettava.tyhjenna();
        }
    }
    
    @Override
    public String toString(){
        return "#" + ketjunTunniste + " " + aihe + " (" + viestit.koko() + " messages)";
    }
    
    
}
