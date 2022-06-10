interface Liste<T> extends Iterable<T>{
    public int stoerrelse();
    public void leggTil(T x);
    public T hent();
    public T fjern();
}
