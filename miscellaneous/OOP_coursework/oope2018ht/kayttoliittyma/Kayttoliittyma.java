package oope2018ht.kayttoliittyma;

import oope2018ht.apulaiset.In;
import oope2018ht.tiedostonlukija.TiedostonLukija;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.viestit.Alue;
import oope2018ht.viestit.Ketju;
import oope2018ht.viestit.Viesti;

/**
 *
 * @author Janis
 */
public class Kayttoliittyma {

    /*
     * Attribuutit
     * Tulkin komennot ovat vakioitu.
     */
    private final String ADD = "add ";
    private final String CATALOG = "catalog";
    private final String SELECT = "select ";
    private final String NEW = "new ";
    private final String REPLY = "reply ";
    private final String TREE = "tree";
    private final String LIST = "list";
    private final String HEAD = "head ";
    private final String TAIL = "tail ";
    private final String EMPTY = "empty ";
    private final String FIND = "find ";
    private final String EXIT = "exit";
    private Alue keskustelualue;
    
    public void paaliittyma() {
        keskustelualue = new Alue();
        System.out.println("Welcome to S.O.B.");
        String kehote;
        do {
            System.out.print(">");
            kehote = In.readString();
            komennonTarkastus(kehote);
        } while (!kehote.equals(EXIT));

    }

    private void komennonTarkastus(String kehote) {
        if (kehote.startsWith(ADD) && kehote.substring(4).length() > 0) {
            add(kehote);           
        } 
        else if (kehote.equals(CATALOG)) {
            catalog();
        } 
        else if (kehote.startsWith(SELECT) && kehote.substring(7).length() > 0) {
            select(kehote);
        } 
        else if (kehote.startsWith(NEW)) {
            newViesti(kehote);
        }
        else if (kehote.equals(TREE)){
            tree();
        }
        else if (kehote.startsWith(REPLY)){
            reply(kehote);
        }
        else if (kehote.equals(LIST)){
            list();
        }
        else if (kehote.startsWith(HEAD)){
            head(kehote);
        }
        else if (kehote.startsWith(TAIL)){
            tail(kehote);
        }
        else if (kehote.startsWith(EMPTY)){
            empty(kehote);
        }
        else if (kehote.startsWith(FIND)){
            find(kehote);
        }
        else if (kehote.equals(EXIT)) {
            System.out.println("Bye! See you soon.");
        } 
        else {
            System.out.println("Error!");
        }
    }
    
    private void add(String kehote){
        String teksti = kehote.substring(4);
        String[] osat = teksti.split(" &");
        if (osat.length == 1) {
            try {
                keskustelualue.lisaaKetju(new Ketju(osat[0], null, keskustelualue));
            } catch (Exception e) {
                System.out.println("Error!");
            }
        } else {
            try {
                TiedostonLukija lukija = new TiedostonLukija();
                Tiedosto tiedosto = lukija.haeTiedosto(osat[1]);
                keskustelualue.lisaaKetju(new Ketju(osat[0], tiedosto, keskustelualue));
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }
   
    private void catalog(){
        keskustelualue.tulostaKetjut();
    }
   
    private void select(String kehote){
        String teksti = kehote.substring(7);
        try{
        keskustelualue.valitseAktiivinen(Integer.parseInt(teksti));
        }catch(IllegalArgumentException e){
            System.out.println("Error!");
        }
    } 
    
    private void newViesti(String kehote) {
        String teksti = kehote.substring(4);
        String[] osat = teksti.split(" &");
        if (osat.length == 1) {
            try {
                keskustelualue.uusiViesti(new Viesti(keskustelualue.haeViestienMaara() + 1,
                        osat[0], null, null));
            } catch (Exception e) {
                System.out.println("Error!");
            }
        } 
        else {
            try {
                TiedostonLukija lukija = new TiedostonLukija();
                Tiedosto tiedosto = lukija.haeTiedosto(osat[1]);
                keskustelualue.uusiViesti(new Viesti(keskustelualue.haeViestienMaara() + 1,
                        osat[0], null, tiedosto));
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }
    
    private void tree(){
        keskustelualue.tulostaPuuna();
    }
    
    private void list(){
        keskustelualue.tulostaListana();
    }
    
    private void reply(String kehote){
        try{
            String tunnisteOsa = kehote.substring(6);
            String[] tunniste = tunnisteOsa.split(" ");
            String tekstiOsa = kehote.substring(6);
            String teksti[] = tekstiOsa.split(" ", 2);
            String[] osat = teksti[1].split(" &");

            Viesti vastattu = keskustelualue.haeViesti(Integer.parseInt(tunniste[0]));
            if(vastattu!=null){
                if (osat.length == 1) {
                    keskustelualue.uusiVastaus(new Viesti(keskustelualue.haeViestienMaara() + 1,
                            osat[0], vastattu, null));
                } else {
                    TiedostonLukija lukija = new TiedostonLukija();
                    Tiedosto tiedosto = lukija.haeTiedosto(osat[1]);
                    keskustelualue.uusiVastaus(new Viesti(keskustelualue.haeViestienMaara() + 1,
                            osat[0], vastattu, tiedosto));
                }
            }
            else
                System.out.println("Error!");
        }catch(Exception e){
            System.out.println("Error!");
        }
    }

    private void head(String kehote){
        try{
            String maara = kehote.substring(5, 6);
            keskustelualue.listaaVanhimmat(Integer.parseInt(maara));
        }
        catch(IllegalArgumentException e){
            System.out.println("Error!");
        }
    }
    
    private void tail(String kehote){
        if(kehote.length()==6){
            try{
                String maara = kehote.substring(5, 6);
                keskustelualue.listaaUusimmat(Integer.parseInt(maara));
            }
            catch(IllegalArgumentException e){
                System.out.println("Error!");
            }
        }
    }

    private void empty(String kehote){
        if(kehote.length()>=7){
            try{
                String luku = kehote.substring(6);
                keskustelualue.tyhjenna(Integer.parseInt(luku));
            }
            catch(IllegalArgumentException e){
                System.out.println("Error!");
            } 
        }
    }

    private void find(String kehote){
        if(kehote.length()>=6){
            try{
                String luku = kehote.substring(5);
                keskustelualue.haeViesti(luku);
            }
            catch(IllegalArgumentException e){
                System.out.println("Error!");
            } 
        }        
    }
}
