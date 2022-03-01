package application.guid;

/**
 * @author Luo Yong
 * @date 2022-1-10
 * @Source 1.0
 */
public class UtilException extends RuntimeException {

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
