import java.util.List;

public class SelectieTimp  implements Strategy {

    @Override
    public int adaugareCoada(Client client, List<Coada> cozi){
        Coada min = cozi.get(0);
        for(Coada c:cozi) {
            if(c.getTime() < min.getTime())
                min = c;
        }
        if(min.adaugareClient(client) == 0)
            return 0;
        return 1;
    }
}
