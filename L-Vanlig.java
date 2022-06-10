public class Vanlig extends Legemiddel {
    public Vanlig(String nv, int prs, double vrkstoff) {
        super(nv, prs, vrkstoff);
    }

    @Override
    public String toString() {
        return ("Vanligmiddel: ID: " + ID + ", Navn: " + navn + ", Pris: " + pris
                + ", Virkestoff: " + virkestoff);
    }

}
