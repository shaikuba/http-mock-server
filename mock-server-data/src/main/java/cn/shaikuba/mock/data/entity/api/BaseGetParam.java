package cn.shaikuba.mock.data.entity.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class BaseGetParam {
    protected static Logger logger = LoggerFactory.getLogger(BaseGetParam.class);

    public String serialize() {
        StringBuilder sb = new StringBuilder("?");
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            sb.append(field.getName()).append("=");

            field.setAccessible(true);

            Object value;

            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                logger.debug("Unable to get field value of {}", field.getName());
                continue;
            }

            sb.append(value).append("&");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
