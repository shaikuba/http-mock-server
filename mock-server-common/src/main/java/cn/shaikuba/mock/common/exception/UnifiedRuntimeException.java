package cn.shaikuba.mock.common.exception;

/**
 * This is for translating a general exception to
 * {@link RuntimeException} .
 *
 * @author JunHo Yoon
 * @since 3.0
 */
public class UnifiedRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 8662535812004958944L;
    private boolean sanitized = false;

    /**
     * Constructor.
     *
     * @param message message
     */
    public UnifiedRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message message
     * @param t       root cause
     */
    public UnifiedRuntimeException(String message, Throwable t) {
        this(message, t, false);
    }

    /**
     * Constructor.
     *
     * @param message   message
     * @param t         root cause
     * @param sanitized sanitized
     */
    public UnifiedRuntimeException(String message, Throwable t, boolean sanitized) {
        super(message, t);
        this.sanitized = sanitized;
    }

    /**
     * Constructor.
     *
     * @param t root cause
     */
    public UnifiedRuntimeException(Throwable t) {
        super(t.getMessage(), t);
    }

    /**
     * Constructor.
     *
     * @param t         root cause
     * @param sanitized sanitized
     */
    public UnifiedRuntimeException(Throwable t, boolean sanitized) {
        super(t.getMessage(), t);
        this.sanitized = sanitized;
    }

    public boolean isSanitized() {
        return sanitized;
    }

    public void setSanitized(boolean sanitized) {
        this.sanitized = sanitized;
    }
}
