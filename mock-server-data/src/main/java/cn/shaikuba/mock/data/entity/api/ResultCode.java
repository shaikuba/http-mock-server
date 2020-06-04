package cn.shaikuba.mock.data.entity.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.lang.reflect.Type;

public enum ResultCode implements Serializable {

    SUCCESS("1", "Operate Success!"),
    FAILURE("0", "Operate Failure!"),

    CANNOT_FIND_LIST("1001", "cannot find list"),
    CANNOT_FIND_MODEL("1002", "cannot find model");

    @Expose
    private String code;

    @Expose
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static class ResultCodeSerializer implements JsonSerializer<ResultCode> {
        @Override
        public JsonElement serialize(ResultCode src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();

            object.addProperty("code", src.getCode());
            object.addProperty("message", src.getMessage());

            return object;
        }

    }

}
