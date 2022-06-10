public class HvitResept extends Resept {
    // konstruktør
    public HvitResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reit) {
        super(legemiddel, lege, pasient, reit);
    }

    @Override
    public String farge() {
        return "Hvit";
    }

    @Override
    public int prisAaBetale() {
        return middel.hentPris();
    }
    
    @Override
    public String toString() {
        return "Hvitresept: ReseptID:" + ID + ", LegemiddelID: " + middel.hentId() + ", Legen: " + legen.hentNavn() +
                ", PasientID: "+pasient.hentId()+", Reit: "+reit + ", Farge: " + farge() + ", Pris: "+prisAaBetale();
    }

}
