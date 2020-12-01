package cn.shaikuba.mock.data.loader.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageConverterAdapter {

    @Autowired
    private List<MessageConverter<String, Object>> messageConverters;

    public String convert(String origin) {
        return messageConverters.stream().filter(messageConverter -> messageConverter.isSupport(origin))
                .findFirst().get().format(origin);
    }
}
