package cn.shaikuba.mock.data.loader.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JsonMessageConverter implements MessageConverter<String, JSONObject> {
    @Override
    public boolean isSupport(String message) {
        return convert(message) != null;
    }

    @Override
    public JSONObject convert(String origin) {

        try {
            JSONObject jsonObject = JSON.parseObject(origin);
            return jsonObject;
        } catch (JSONException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String format(String origin) {
        return convert(origin).toJSONString();
    }

}
