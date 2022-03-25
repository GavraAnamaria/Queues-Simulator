import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class Simulare implements Runnable{
    private int timp = 0;
    private int timpMaximProcesare;
    private int timpMinimProcesare;
    private int timpMaximSosire;
    private int timpMinimSosire;
    private int nrCozi;
    private int nrClienti;
    private MetodeDeSelectie metoda;
    //private int dimCoada = 15;

    static FileWriter fileWriter;
    public static int timpCurent = 0;
    private Magazin magazin;
    private final JFrame f;
    private View view;
    private List<Client> clienti ;

    public Simulare() throws IOException {
        clienti = new ArrayList<>();
        fileWriter = new FileWriter("C:\\Users\\Anamaria\\Desktop\\a.txt");
        magazin = new Magazin(0,0,MetodeDeSelectie.timp);
        view = new View(magazin);
        view.addStartListener(new startListener());
        f = new JFrame();
        f.setSize(1700,500);
        f.setContentPane(view);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    public void setData(int t, int tMinS, int tMaxS, int tMinP, int tMaxP, int nrC, int nrCl, MetodeDeSelectie m) {
        timp = t;
        timpMaximProcesare = tMaxP;
        timpMinimProcesare = tMinP;
        timpMaximSosire = tMaxS;
        timpMinimSosire = tMinS;
        nrCozi = nrC;
        nrClienti =nrCl;
        metoda = m;
    }

    private void generareClienti() {
        Random rand = new Random();
        for(int i = 0; i < nrClienti; i++) {
            clienti.add(new Client(i, timpMinimSosire + rand.nextInt(timpMaximSosire - timpMinimSosire+1), timpMinimProcesare + rand.nextInt(timpMaximProcesare - timpMinimProcesare+1)));
        }
        Collections.sort(this.clienti);
    }

    @Override
    public void run(){
        try {
            while(timp == 0)
                Thread.sleep(100);
            magazin = new Magazin(nrCozi, 15, metoda);
            view = new View(magazin);
            int maxim = 0;
            int tMediuAsteptare = 0;
            int tMediuProcesare = 0;
            int tMaxim = 0;
            generareClienti();
            while(timpCurent < timp) {
                fileWriter.write("\n\n\n     T = " + timpCurent + "\n");

                while(clienti.size() > 0 ) {
                    Client c = clienti.get(0);
                    if(c.getTSosire() <= timpCurent && magazin.adaugare(c) == 0) {
                        tMediuAsteptare += c.getTPlecare() - c.getTSosire();
                        tMediuProcesare += c.getTProcesare();
                        clienti.remove(0);
                    }else
                        break;

                }
                view.revalidate();
                view.repaint();
                f.setContentPane(view);
                int suma = 0;
                for(Coada c:magazin.getStatus()) {
                    suma += c.getTime();
                }
                if(suma > maxim) {
                    tMaxim = timpCurent;
                    maxim = suma;
                }
                timpCurent++;
                Thread.sleep(1000);
            }
            fileWriter.write("\n******************************************************************************\n->Ora de varf: " + tMaxim + "(" + maxim + ")\n->Timp mediu de asteptare: " + tMediuAsteptare/nrClienti + "\n->Timp mediu de servire: " + tMediuProcesare/nrClienti);
            fileWriter.close();

        }catch (Exception e) {
            System.out.println("Intrerupere!!!");
            e.printStackTrace();
        }

    }

    class startListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setData(Integer.parseInt(view.getTSimulare()), Integer.parseInt(view.getTMinSosire()), Integer.parseInt(view.getTMaxSosire()),Integer.parseInt(view.getTMinProcesare()), Integer.parseInt(view.getTMaxProcesare()), Integer.parseInt(view.getNrCozi()), Integer.parseInt(view.getNrClienti()), view.getCriteriu());
        }
    }

    public static void main(String[] args) throws IOException {

        Simulare s = new Simulare();
        Thread t = new Thread(s);
        t.start();
    }
}
