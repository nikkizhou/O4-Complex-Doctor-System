public class Narkotisk extends Legemiddel {
    private int styrke;

    public Narkotisk(String nv, int prs, double vrkstoff, int styrk) {
        super(nv, prs, vrkstoff);
        styrke = styrk;
    }

    public int hentNarkotiskStyrke() {
        return styrke;
    }

    @Override
    public String toString() {
        return ("Narkotiskmiddel: ID: " + ID + ", Navn: " + navn + ", Pris: " + pris
                + ", Virkestoff: " + virkestoff + ", Styrke: " + styrke);
    }
}
