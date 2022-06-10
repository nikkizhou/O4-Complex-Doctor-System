public class BlaResept extends Resept {
    public BlaResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reitt) {
        super(legemiddel, lege, pasient, reitt);
    }

    @Override
    public String farge() {
        return "Blaa";
    }

    @Override
    public int prisAaBetale() {
        return (int) Math.round(middel.hentPris() * 0.25);
    }

    @Override
    public String toString() {
        return "Blaa resept: ReseptID:" + ID + ", LegemiddelID: " + middel.hentId() + ", Legen: " + legen.hentNavn() +
                ", PasientID: " + pasient.hentId() + ", Reit: " + reit + ", Farge: " + farge() + ", Pris: " + prisAaBetale();
    }

}
