public class IndeksertListe<T> extends Lenkeliste<T> {

    public void leggTil(int pos, T x) {

        Node nyNode = new Node(x);

        // hvis position man skal legge nyNode til er neste positionen av [Node slutt],
        // saa legge nyNode til slutten.
        if (pos == stoerrelse()) {
            leggTil(x);
            return;
        // hvis position man skal legge nyNode til er bak neste positionen av
        // [Node slutt], sa throw UgyldigListeIndeks.
        } else if (pos > stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);

        // hvis position man skal legge nyNode til er 0, saa legge nyNode til starten,
        // og [variablen start] refererer til nyNode.
        } else if (pos == 0) {
            leggTilForst(x);
            return;
        }

        Node peker = finnPeker(pos);

        // f.eks.[pos er 2], og peker refererer til Node2: Node0 ⇆ Node1 ⇆ Node2* ⇆
        // Node3
        peker.forrige.neste = nyNode; // Node0 ⇆ Node1 → [nyNode] Node2* ⇆ Node3
        nyNode.forrige = peker.forrige; // Node0 ⇆ Node1 ⇆ [nyNode] Node2* ⇆ Node3
        peker.forrige = nyNode; // Node0 ⇆ Node1 ⇆ [nyNode] left Node2* ⇆ Node3
        nyNode.neste = peker; // Node0 ⇆ Node1 ⇆ [nyNode] ⇆ Node2* ⇆ Node3
    }


    public T fjern(int pos) {
        // hvis posisjonen vi skal ferne Node er storre enn eller lik lenkeliste
        // stoerrelse, som inkludere naar pos=stoerrelse=0, throw UgyldigListeIndeks.
        if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);

            // hvis posisjonen vi skal ferne Node er siste posisjonen i lenkelisten
            // inkluederer naar storrelse=1 og pos=0;
        } else if (pos == stoerrelse() - 1) {
            return fjernSiste();

            // hvis posisjonen vi skal ferne Node er forste posisjonen i lenkelisten, og
            // lenkelisten er ikke tom
        } else if (pos == 0) {
            return fjern();
        }
        // [variablen peker] skal referere til den Noden i positionen [pos]
        Node peker = finnPeker(pos);
        // f.eks.[pos er 2], peker refererer til Node2: Node0 ⇆ Node1 ⇆ Node2* ⇆ Node3
        peker.forrige.neste = peker.neste; // Node0 ⇆ Node1 → Node3
        peker.neste.forrige = peker.forrige; // Node0 ⇆ Node1 ⇆ Node3

        return peker.data;
    }


    public T hent(int pos) {

        if (start == null) {
            throw new UgyldigListeIndeks(-1);
        } else if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        }

        return finnPeker(pos).data;
    }


    public void sett(int pos, T x) {
        if (start == null) {
            throw new UgyldigListeIndeks(-1);
        } else if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        }

        finnPeker(pos).data = x;
    }


    public Node finnPeker(int pos) {
        Node peker = start;

        for (int i = 0; i < pos; i++) {
            peker = peker.neste;
        }
        return peker;
    }

    public boolean erTom() {
        return start == null;
    }
}
