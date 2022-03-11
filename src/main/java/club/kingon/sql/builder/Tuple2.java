package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2022/1/19 9:33
 */
public class Tuple2<P, T> {
    public P _1;
    public T _2;

    private Tuple2(P _1, T _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <P, T>Tuple2<P, T> of(P _1, T _2) {
        return new Tuple2<>(_1, _2);
    }
}
