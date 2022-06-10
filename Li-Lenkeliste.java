import java.util.Iterator;
import java.lang.Iterable;

public class Lenkeliste<T> implements Iterable<T>,Liste<T>{
    // class Node er indre klasse
    protected class Node {
        Node neste = null;
        Node forrige = null;
        T data;
        int antall;

        public Node(T x) {
            data = x;
        }
    }

    protected Node start = null;
    protected Node slutt = null;

    public Iterator<T> iterator(){
        return new LenkelisteIterator();
    }

    protected class LenkelisteIterator implements Iterator<T>{
        private int pos = 0;

        public T next() {
            Node peker = start;

            for (int i = 0; i < pos; i++) {
                peker = peker.neste;
            }

            pos++;
            return peker.data;
        }

        public boolean hasNext(){
            return (pos < stoerrelse());
        }
    }


    @Override
    // legg ny Node til siste plass i lenkelisten.
    public void leggTil(T x) {

        Node nyNode = new Node(x);

        // Hvis lenkelisten er tom: 
        if (start == null) {
            start = nyNode; // start referer til den nye noden med data x 
            slutt = start; // la [variablen slutt] referere til [Noden start] ogsa.

            // hvis lengkelisten ikke er tom: 
        } else {
            slutt.neste = nyNode; // 'neste' av [Noden slutt] skal referere til den nye Noden.
            nyNode.forrige = slutt; // 'forrige' av [nyNode] skal referere til [Noden slutt].
            slutt = nyNode; // [variablen slutt] flyttes til den nye Noden.
        }
    }


    // legg ny Node til forste plass i lenkelisten.
    public void leggTilForst(T x) {
        Node nyNode = new Node(x);
        if (start == null) {
            start = nyNode;
            slutt = start;
        } else {
            start.forrige = nyNode;
            nyNode.neste = start;
            start = nyNode;
        }
    }
   
    @Override
    // fjern forste noden i lenkelisten.
    public T fjern() {
        // Hvis lenkelisten er tom, throw UgyldigListeIndeks.
        if (start == null) {
            throw new UgyldigListeIndeks(-1);
        }
        // hvis lenkelisten ikke er tom:
        Node temp = start; // Lag en ny variabel [temp] som refererer til [Noden start] som skal returneres.
        start = start.neste; // flytte [variablen start] til neste Node
        return temp.data;
    }

    // fjern siste noden i lenkelisten.
    public T fjernSiste() {
        Node temp =slutt;

        // Hvis Listen er tom, throw UgyldigListeIndeks.
        if (start == null) {
            throw new UgyldigListeIndeks(-1);
        //Hvis Listen har bare en Node
        } else if (stoerrelse() == 1) {   
            slutt = null;
            start = null;
        // hvis Listen ikke er tom og er flere enn en node:
        } else {
            slutt = slutt.forrige; // flytte [variablen slutt] til forrige Node
            slutt.neste = null; 
        }
        return temp.data; 
    } 

    @Override
    public int stoerrelse() {
        int indeks = 0;
        Node peker = start;

        while (peker != null) { 
            indeks++;
            peker = peker.neste;
        }
        return indeks;
    }

    @Override
    public T hent() {
        if (start == null) {
            return null;
        }

        return start.data;
    }

    public T hent(int pos) {

        if (start == null) {
            throw new UgyldigListeIndeks(-1);
        } else if (pos >= stoerrelse() || pos < 0) {
            throw new UgyldigListeIndeks(pos);
        }

        return finnPeker(pos).data;
    }

    public Node finnPeker(int pos) {
        Node peker = start;

        for (int i = 0; i < pos; i++) {
            peker = peker.neste;
        }
        return peker;
    }


    @Override
    public String toString() {
        Node peker = start;
        String strengen = "";

        while (peker != null) {
            strengen += peker.data.toString() + " ";
            peker = peker.neste;
        }
        return strengen;
    }
}
