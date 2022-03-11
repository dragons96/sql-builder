package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/10 13:04
 */
public class SqlBuilderException extends RuntimeException {
    public SqlBuilderException() {
    }

    public SqlBuilderException(String message) {
        super(message);
    }

    public SqlBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlBuilderException(Throwable cause) {
        super(cause);
    }

    public SqlBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
