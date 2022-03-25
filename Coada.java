import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Coada implements Runnable{
    private BlockingQueue<Client> coada;
    private AtomicInteger waitingTime;
    private int dimCoada;
    private int nr;


    public Coada(int dimCoada, int nr ){
        this.nr = nr;
        this.dimCoada = dimCoada;
        coada = new LinkedBlockingQueue<>(dimCoada);
        waitingTime = new AtomicInteger();
    }

    public int adaugareClient(Client c) {

        if (coada.size() < dimCoada) {
            try {
                coada.add(c);
                waitingTime.getAndAdd(c.getTProcesare());
                c.setTPlecare(waitingTime.get() + Simulare.timpCurent + 1);
                Simulare.fileWriter.write(c +"\n->Clientul a fost adaugat in coada " + nr + "\n -----------------------------------[ COADA " + nr + " -> " + waitingTime.get() + " ]------------------------------");
            }catch(Exception e) {
                System.out.println("Eroare de scriere/intrerupere la adaugare!\n");
                e.printStackTrace();
            }
            return 0;
        }else
            return 1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if(coada.size()>0) {
                    Client c = coada.take();
                    while(c.getTPlecare() > Simulare.timpCurent) {
                        Thread.sleep(800);
                    }
                    System.out.println(c.getTPlecare() + " - > " +  Simulare.timpCurent +"\n");
                    waitingTime.getAndAdd(-c.getTProcesare());
                    Simulare.fileWriter.write(c +"\n->Clientul a fost sters din coada " + nr + "\n -----------------------------------[ COADA " + nr + " -> " + waitingTime.get()+ " ]------------------------------");
                }
            }catch (Exception e) {
                System.out.println("Eroare de scriere/intrerupere la stergere!\n");
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Client> getStatusCoada(){
        return this.coada;
    }

    public int getTime() {
        return waitingTime.get();
    }

}
