abstract public class Legemiddel {
    protected int ID;
    protected static int teller = 0;
    protected String navn;
    protected int pris;
    protected double virkestoff;

    public Legemiddel(String nv, int prs, double vrkstoff) {
        navn = nv;
        pris = prs;
        virkestoff = vrkstoff; 
        ID = ++teller;
    }

    public int hentId() {
        return ID;
    }

    public String hentNavn() {
        return navn;
    }

    public int hentPris() {
        return pris;
    }

    public double hentVirkestoff() {
        return virkestoff;
    }

    public void settNyPris(int prs) {
        pris = prs;
    }

    abstract public String toString();
}
