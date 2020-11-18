package cn.shaikuba.mock.service.behavior.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JsonMessageConverter implements MessageConverter<String, String> {
    @Override
    public boolean isSupport(MessageType messageType) {
        return MessageType.JSON.equals(messageType);
    }

    @Override
    public String convert(String origin) {

        try {
            JSONObject jsonObject = JSON.parseObject(origin);
            return jsonObject.toJSONString();
        } catch (JSONException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
