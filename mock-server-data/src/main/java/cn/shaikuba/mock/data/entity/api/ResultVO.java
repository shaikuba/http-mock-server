package cn.shaikuba.mock.data.entity.api;

import lombok.ToString;

@ToString
public class ResultVO<T> {

    private boolean success;

    private String code;

    private String message;

    private T data;

    public ResultVO() {
    }

    private ResultVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T>ResultVO<T> success() {
        return success("Operation Successfully!");
    }

    public static <T>ResultVO<T> success(String message) {
        return success("0000", message);
    }

    public static <T>ResultVO<T> success(String code, String message) {
        ResultVO result = new ResultVO();
        result.withCode(code);
        result.setSuccess(true);
        result.withMessage(message);

        return result;
    }

    public static <T> ResultVO<T> fail() {
        return fail("Operation Failed!");
    }

    public static <T>ResultVO<T> fail(String errMsg) {
        return fail("9999", errMsg);
    }

    public static <T>ResultVO<T> fail(String errorCode, String errMsg) {
        ResultVO result = new ResultVO();
        result.withCode(errorCode);
        result.setSuccess(false);
        result.withMessage(errMsg);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultVO<T> setSuccess(boolean isSuccess) {
        this.success = isSuccess;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResultVO<T> withCode(String code) {
        this.success = false;
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultVO<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultVO<T> withData(T data) {
        this.data = data;
        return this;
    }

}