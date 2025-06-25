package a088;

public class Pair<T> {

    private T first;
    private T last;


    public Pair(T first, T last) {
        this.first = first;
        this.last = last;
    }





    public static <K> Pair<K> create(K first, K last) {
        return new Pair<K>(first, last);
    }

    public static void main(String[] args) throws Exception {
        Pair<String> p1 = new Pair<>("Hello", "world");


        System.out.println(p1.first.hashCode());
        System.out.println(p1.first);

    }
}
