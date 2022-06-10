import java.util.ArrayList;

public class Spesialister extends Lege implements Godkjenningsfritak {
    private String kontrollID;

    public Spesialister(String nv, String kontrollId) {
        super(nv);
        kontrollID = kontrollId;
    }

    @Override
    public String hentKontrollID() {
        return kontrollID;
        
    }
    
    @Override
    public String toString() {
        return navn + ", KontrollID: " + kontrollID + ", UtskrevneResepter:" + hentResepterID();
    }

}
