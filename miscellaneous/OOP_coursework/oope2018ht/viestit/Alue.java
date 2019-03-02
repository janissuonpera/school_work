package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;

/**
 *
 * @author Janis
 */
public class Alue {
    private int keskustelut;
    private OmaLista ketjut;
    private int aktiivinen;
    private int viestienMaara;
    
    //OletusRakentaja
    public Alue(){
        asetaKeskustelut(0);
        asetaKetjut(new OmaLista());
        
    }
    
    @Setteri
    public void asetaKeskustelut(int keskustelut)throws IllegalArgumentException{
        if(keskustelut>=0)
            this.keskustelut = keskustelut;
        else
            throw new IllegalArgumentException();
    }
   
    @Getteri
    public int haeKeskustelut(){
        return keskustelut;
    }
    
    @Setteri
    public void asetaKetjut(OmaLista ketjut)throws IllegalArgumentException{
        if(ketjut!=null)
            this.ketjut = ketjut;
        else
            throw new IllegalArgumentException();
    }
    
    @Getteri
    public OmaLista haeKetjut(){
        return ketjut;
    }

    @Getteri
    public int haeViestienMaara() {
        return viestienMaara;
    }
    
    @Setteri
    public void asetaViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }
    @Getteri
    public int getAktiivinen() {
        return aktiivinen;
    }
    
    
    public void lisaaKetju(Ketju k){
        if(k!=null){
            ketjut.lisaaLoppuun(k);
            keskustelut ++;
            if(ketjut.koko()==1)
                aktiivinen = 1;
        }
    }
    
    public void tulostaKetjut(){
        if(ketjut!=null && !ketjut.onkoTyhja()){
            for(int i=0; i<keskustelut; i++){
                System.out.println(ketjut.alkio(i));
            }
        }
    }
    
    public void valitseAktiivinen(int x)throws IllegalArgumentException{
        if(x<1 || x>ketjut.koko())
            throw new IllegalArgumentException();
        else
            aktiivinen = x;
    }
    
    public void uusiViesti(Viesti viesti)throws IllegalArgumentException {
        if (viesti != null && ketjut != null) {
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen - 1);
            aktiivinenKetju.lisaaViesti(viesti);
            viestienMaara ++;
        }
        else
            throw new IllegalArgumentException();
    }
    

    public void uusiVastaus(Viesti viesti)throws IllegalArgumentException{
        if(viesti!=null && ketjut!=null){
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen - 1);
            aktiivinenKetju.lisaaViesti(viesti);
            viestienMaara ++;
        }
        else
            throw new IllegalArgumentException();    
    }
    
    
    public Viesti haeViesti(int tunniste)throws IllegalArgumentException{
        if(tunniste>0 && tunniste<=viestienMaara){
            Ketju ketju = (Ketju) haeKetjut().alkio(aktiivinen-1);
            Viesti haettava = ketju.haeViesti(tunniste);
            return haettava;
        }
        else
            throw new IllegalArgumentException();
    }
    
    public void haeViesti(String teksti){
        if(teksti.length()>0){
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen -1);
            aktiivinenKetju.haeViesti(teksti);
        }
    }
    
    public void tulostaListana(){
        if(ketjut !=null){
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen - 1);
            aktiivinenKetju.tulostaListana();
        }
    }
    
    public void tulostaPuuna(){
        if(ketjut !=null){
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen - 1);
            aktiivinenKetju.tulostaPuuna();
        }
    }
    

    public void listaaVanhimmat(int x){
        Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen -1);
        aktiivinenKetju.listaaVanhimmat(x);
    }

    public void listaaUusimmat(int x){
        Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen -1);
        aktiivinenKetju.listaaUusimmat(x);    
    }

    public void tyhjenna(int x){
        if(x>0 && x<=viestienMaara){
            Ketju aktiivinenKetju = (Ketju) ketjut.alkio(aktiivinen -1);
            aktiivinenKetju.tyhjenna(x);
        }
    }
}
