
import java.util.LinkedList;
import java.util.List;


public class Magazin {
    private List<Coada> cozi;
    private Strategy strategie;


    public Magazin( int nrCozi, int dimCoada, MetodeDeSelectie metoda ){
        cozi = new LinkedList<>();

        if(metoda == MetodeDeSelectie.timp)
            strategie = new SelectieTimp();
        if(metoda == MetodeDeSelectie.lungime)
            strategie = new SelectieLungime();

        for( int i = 0; i < nrCozi; i++){
            Coada c = new Coada(dimCoada, i);
            Thread t = new Thread(c);
            cozi.add(c);
            t.start();
        }
    }

    public List<Coada> getStatus() {
        return cozi;
    }




    public int adaugare(Client c) {
        if(strategie.adaugareCoada(c, cozi) == 0)
            return 0;
        return 1;
    }


}
