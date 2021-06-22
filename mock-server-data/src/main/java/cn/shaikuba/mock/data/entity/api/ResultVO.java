package cn.shaikuba.mock.data.entity.api;

import cn.shaikuba.mock.data.entity.base.Pageable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ResultVO<T> {

    private boolean status;

    private String code;

    private String message;

    private T data;

    @Setter
    @Getter
    private Pageable pageable;

    public ResultVO() {
    }

    private ResultVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResultVO<T> success() {
        return success("Operation Success!");
    }

    public static <T> ResultVO<T> success(String message) {
        return success("0000", message);
    }

    public static <T> ResultVO<T> success(String code, String message) {
        return success(code, message, null);
    }

    public static <T> ResultVO<T> success(String code, String message, T data) {
        ResultVO<T> result = new ResultVO();
        result.withCode(code);
        result.withStatus(true);
        result.withMessage(message);
        result.withData(data);
        return result;
    }

    public static <T> ResultVO<T> fail() {
        return fail("Operation Failed!");
    }

    public static <T> ResultVO<T> fail(String errMsg) {
        return fail("9999", errMsg);
    }

    public static <T> ResultVO<T> fail(String errorCode, String errMsg) {
        return fail(errorCode, errMsg, null);
    }

    public static <T> ResultVO<T> fail(String errorCode, String errMsg, T data) {
        ResultVO<T> result = new ResultVO();
        result.withCode(errorCode);
        result.withStatus(false);
        result.withMessage(errMsg);
        result.withData(data);
        return result;
    }

    public boolean getStatus() {
        return status;
    }

    public ResultVO<T> withStatus(boolean isSuccess) {
        this.status = isSuccess;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResultVO<T> withCode(String code) {
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