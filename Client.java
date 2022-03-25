public class Client  implements Comparable<Client> {
    private final int ID;
    private final int timpSosire;
    private final int timpProcesare;
    private int timpPlecare;

    public Client(int ID, int timpSosire, int timpProcesare) {
        this.ID = ID;
        this.timpSosire = timpSosire;
        this.timpProcesare = timpProcesare;
    }

    public int getID() {
        return ID;
    }

    public int getTProcesare() {
        return timpProcesare;
    }

    public int getTSosire() {
        return timpSosire;
    }

    public void setTPlecare(int t) {
        this.timpPlecare = t;
    }

    public int getTPlecare() {
        return timpPlecare;
    }

    @Override
    public int compareTo(Client c) {
        return Integer.compare(this.timpSosire, c.timpSosire);
    }


    @Override
    public String toString() {
        return "\nID: " + ID + "\n" + "Timp Sosire: " + timpSosire + "\n" + "Timp Procesare: " + timpProcesare + "\n" ;
    }
}

