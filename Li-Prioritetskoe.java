public class Prioritetskoe<T extends Comparable<T>> extends IndeksertListe<T> {
    // her extends jeg IndeksertListe<T> istedenfor kun Lenkeliste 
    //fordi jeg skal bruke metoden leggTil(int pos,T x)

    @Override
    public void leggTil(T x) {

        //hvis listen er tom, eller x er storre enn alle data, bruk leggeTil fra Lenkeliste.
        if (start == null || x.compareTo(slutt.data) > 0) {
            super.leggTil(x);

        // hvis x er mindre enn alle data, legge nyNode til forste plassen.
        } else if (x.compareTo(start.data) < 0) {
            super.leggTilForst(x);

        // hvis listen ikke er tom, samt nyNode skal ikke vaere i slutten av lenkelisten:
        } else {
            int indeks = 0;
            for (int i = 0; i < stoerrelse(); i++) {
                //Finn forste node som har data storre enn eller lik x.
                if (x.compareTo(hent(i)) >= 0) { 
                    indeks++;
                }
            }
            // Legg nyNode foran peker som ble funnet.
            super.leggTil(indeks, x);
        }
    }


    // i prioritetskoe skal metodene nede deactivated for aa unngaa at prioritetskoe blir feil rekkefoelge.

    @Override
    public void leggTilForst(T x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggTil(int pos, T x) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void sett(int pos, T x) {
        throw new UnsupportedOperationException();
    }
}
