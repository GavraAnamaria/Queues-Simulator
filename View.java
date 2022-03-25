import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.io.Serial;

import javax.swing.*;

class View extends JPanel{

    @Serial
    private static final long serialVersionUID = 1L;
    private final JTextField tSimulare = new JTextField(4);
    private final JTextField tMinProcesare = new JTextField(4);
    private final JTextField tMaxProcesare = new JTextField(4);
    private final JTextField tMaxSosire = new JTextField(4);
    private final JTextField tMinSosire = new JTextField(4);
    private final JTextField nrClienti = new JTextField(4);
    private final JTextField nrCozi = new JTextField(4);
    private final JComboBox<String> selectie = new JComboBox<>();
    private final JButton start = new JButton("START");
    private Magazin magazin;

    public View(Magazin m) {
        magazin = m;
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Numar de clienti:"));
        this.add(nrClienti);
        this.add(new JLabel("  Numar de cozi:"));
        this.add(nrCozi);
        this.add(new JLabel("  Timp minim de sosire:"));
        this.add(tMinSosire);
        this.add(new JLabel("  Timp maxim de sosire:"));
        this.add(tMaxSosire);
        this.add(new JLabel("  Timp minim de procesare:"));
        this.add(tMinProcesare);
        this.add(new JLabel("  Timp maxim de procesare:"));
        this.add(tMaxProcesare);
        this.add(new JLabel("  Durata simularii:"));
        this.add(tSimulare);
        selectie.addItem("Timp de asteptare");
        selectie.addItem("Lungime");
        this.add(new JLabel("  Criteriu selectare coada:"));
        this.add(selectie);
        this.add(start);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int j=1;
        for(Coada c:magazin.getStatus()) {
            g.setColor(new Color(141, 23, 199));
            g.fillRect(10, 100 *j, 80, 80);
            int i=1;
            for(Client cl:c.getStatusCoada()){
                g.setColor(new Color(cl.getID()*2 % 225 ,0 , cl.getID()*2%225));
                g.fillOval(40+90*i, 100*j, 80, 80);
                i++;
            }
            j++;
        }

    }

    String getTMinSosire() {
        return tMinSosire.getText();
    }

    String getTMaxSosire() {
        return tMaxSosire.getText();
    }

    String getTMinProcesare() {
        return tMinProcesare.getText();
    }
    String getTMaxProcesare() {
        return tMaxProcesare.getText();
    }

    String getTSimulare() {
        return tSimulare.getText();
    }

    String getNrClienti() {
        return nrClienti.getText();
    }

    String getNrCozi() {
        return nrCozi.getText();
    }

    MetodeDeSelectie getCriteriu() {
        if(selectie.getSelectedItem() == "Timp de asteptare")
            return MetodeDeSelectie.timp;
        else
            return MetodeDeSelectie.lungime;
    }

    void addStartListener(ActionListener l) {
        start.addActionListener(l);
    }
}
