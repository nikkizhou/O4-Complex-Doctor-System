import java.util.ArrayList;

public class Lege implements Comparable<Lege>{
    protected String navn;
    protected IndeksertListe<Resept> utskrevneResepter = new IndeksertListe<>();

    public Lege(String nv) {
        navn = nv;
    }

    public String hentNavn(){
        return navn;
    }

    public String toString() {
        return navn+", KontrollID:0" + ", UtskrevneResepter:" + hentResepterID();
    }

    public int compareTo(Lege annenLege) {
        //gaar gjennom alle bokstaver i det navnet som har mindre antall bokstaver
        for (int i = 0; i < Math.min(navn.length(), annenLege.navn.length()); i++) {
            //hvis bokstaver i samme position ikke er like
            if ((int) navn.charAt(i) != (int) annenLege.navn.charAt(i))
                return (int) navn.charAt(i) - (int) annenLege.navn.charAt(i);
        }

        // f.eks hvis navn er  Andre og annenLege.navn er Andres, saa blir det negativt tall;
        if (navn.length() != annenLege.navn.length()) {
            return navn.length() - annenLege.navn.length();
            // f.eks hvis navn er Andre og annenLege.navn er Andres, saa blir det null;
        } else {
            return 0;
        }

    }

    public IndeksertListe<Resept> hentReseptene() {
        return utskrevneResepter;
    }

    public ArrayList<Integer> hentResepterID() {
        ArrayList resepterID = new ArrayList<>();

        for (Resept resept : utskrevneResepter) {
            resepterID.add(resept.hentId());
        }
        return resepterID;
    }

    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        HvitResept hvitResepten = new HvitResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(hvitResepten);
        return hvitResepten;
    }

    public MilResept skrivMilResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        MilResept milResepten = new MilResept(legemiddel, this, pasient);
        utskrevneResepter.leggTil(milResepten);
        return milResepten;
    }

    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        if (legemiddel instanceof Narkotisk) {
            throw new UlovligUtskrift(this, legemiddel);
        }
        PResept PResepten = new PResept(legemiddel, this, pasient, reit);
        utskrevneResepter.leggTil(PResepten);
        return PResepten;
        
    }

    public BlaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
        BlaResept blaaResepten = new BlaResept(legemiddel, this, pasient, reit);
        
        if (legemiddel instanceof Narkotisk) {
            if (this instanceof Spesialister == false) {
                throw new UlovligUtskrift(this, legemiddel);
            }
        }
        utskrevneResepter.leggTil(blaaResepten);
        return blaaResepten;

    }
}
