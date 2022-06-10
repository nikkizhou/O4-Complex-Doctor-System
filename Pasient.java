import java.util.ArrayList;

public class Pasient {
    private String navn;
    private String foedNr;
    private int ID;
    private static int teller = 0;
    private ArrayList<Resept> resepter = new ArrayList<>();
    
    public Pasient(String pasientNavn, String foedselNr) {
        navn = pasientNavn;
        foedNr = foedselNr;
        ID = ++teller;
    }
    
    public int hentId() {
        return ID;
    }
    
    public String toString() {
        return "Navn: " + navn + ", Foedselnummer: " + foedNr + ", ID: " + ID + ",  Alle resepter ID: " + hentResepterID();
    }

    public ArrayList<Integer> hentResepterID() {
        ArrayList<Integer> resepterID = new ArrayList<>();
        for (Resept resept : resepter) {
            resepterID.add(resept.hentId());
        }
        return resepterID;
    }

    public ArrayList<Resept> alleResepter(){
        return resepter;
    }

    public void leggNyResept(Resept resept) {
        resepter.add(resept);
    }

    public String hentNavn() {
        return navn;
    }

    public String hentFNr() {
        return foedNr;
    }

    public String hentKortInfo() {
        return navn + " (fnr " + foedNr +')';
    }






    
}
