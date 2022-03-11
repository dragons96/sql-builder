package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2022/3/10 16:11
 */
public class LambdaException extends RuntimeException {

    public LambdaException() {
    }

    public LambdaException(String message) {
        super(message);
    }

    public LambdaException(String message, Throwable cause) {
        super(message, cause);
    }

    public LambdaException(Throwable cause) {
        super(cause);
    }

    public LambdaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
