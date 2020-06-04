package cn.shaikuba.mock.data.entity.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface BaseGson {

    default String serialize() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
