public class Vanedannende extends Legemiddel {
    private int styrke;

    public Vanedannende(String nv, int prs, double vrkstoff, int styrk) {
        super(nv, prs, vrkstoff);
        styrke = styrk;
    }

    public int hentVanedannendeStyrke() {
        return styrke;
    }

    @Override
    public String toString() {
        return ("Vanedannendemiddel: ID: " + ID + ", Navn: " + navn + ", Pris: " + pris
                + ", Virkestoff: " + virkestoff + ", Styrke: " + styrke);
    }
}
