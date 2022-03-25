import java.util.List;
public class SelectieLungime implements Strategy {

    @Override
    public int adaugareCoada(Client client, List<Coada> cozi){
        Coada min = cozi.get(0);
        for(Coada c:cozi) {
            if(c.getStatusCoada().size() < min.getStatusCoada().size())
                min = c;
        }
        if(min.adaugareClient(client) == 0)
            return 0;
        return 1;
    }

}
