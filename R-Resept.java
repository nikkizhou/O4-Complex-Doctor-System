abstract public class Resept {
    protected static int teller;
    protected int ID;
    protected Legemiddel middel;
    protected Lege legen;
    protected Pasient pasient;
    protected int reit; // Hvis antall ganger igjen er 0, er resepten ugyldig

    // konstruktør
    public Resept(Legemiddel legemiddel, Lege lege, Pasient pasientO, int reitt) {
        middel = legemiddel;
        legen = lege;
        pasient = pasientO;
        reit = reitt;
        ID=++teller;

    }

    public int hentId() {
        return ID;

    }

    public Legemiddel hentLegemiddel() {
        return middel;
    }

    public Lege hentLege() {
        return legen;

    }

    public int hentPasientId() {
        return pasient.hentId();
    }

    public Pasient hentPasient() {
        return pasient;
    }

    public int hentReit() {
        return reit;
    }

    public boolean bruk() {
        if (reit > 0) {
            reit--;
            return true;
        }
        return false;
    }

    public String hentKortInfo() {
        return middel.hentNavn() + " (" + Integer.toString(reit) + " reit)";
    }

    abstract public String farge();

    abstract public int prisAaBetale();

    abstract public String toString();
}
