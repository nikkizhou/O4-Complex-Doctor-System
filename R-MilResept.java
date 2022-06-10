public class MilResept extends HvitResept { 
    public MilResept(Legemiddel legemiddel, Lege lege, Pasient pasient) {
        super(legemiddel, lege, pasient, 3);
    }

    @Override            // try deleting
    public String farge() {
        return "Hvit";
    }

    @Override
    public int prisAaBetale() {
        return 0;
    }

    @Override
    public String toString() {
        return "Militarresept: ReseptID:" + ID + ", LegemiddelID: " + middel.hentId() + ", Legen: " + legen.hentNavn() +
                ", PasientID: "+pasient.hentId()+", Reit: "+reit + ", Farge: " + farge() + ", Pris: "+prisAaBetale();
    }
}
