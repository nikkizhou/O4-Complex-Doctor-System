public class PResept extends HvitResept { 
    static int rabatt = 108;

    public PResept(Legemiddel legemiddel, Lege lege, Pasient pasient, int reitt) {
        super(legemiddel, lege, pasient, reitt);
    }

    @Override     // try deleting
    public String farge() {
        return "Hvit";
    }

    @Override
    public int prisAaBetale() {
        if (middel.hentPris() - rabatt < 0) {
            return 0;
        }
        return middel.hentPris() - rabatt;
    }
    
    @Override
    public String toString() {
        return "P-resept: ReseptID:" + ID + ", LegemiddelID: " + middel.hentId() + ", Legen: " + legen.hentNavn() +
                ", PasientID: " + pasient.hentId() + ", Reit: " + reit + ", Farge: " + farge() + ", Pris: " + prisAaBetale();
    }

}
