package cn.shaikuba.mock.common.exception;

public class MockSecurityApiException extends Exception {
    private static final long serialVersionUID = -238091758285157331L;
    private int errCode;
    private String errMsg;

    public MockSecurityApiException() {
    }

    public MockSecurityApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MockSecurityApiException(String message) {
        super(message);
    }

    public MockSecurityApiException(Throwable cause) {
        super(cause);
    }

    public MockSecurityApiException(int errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
