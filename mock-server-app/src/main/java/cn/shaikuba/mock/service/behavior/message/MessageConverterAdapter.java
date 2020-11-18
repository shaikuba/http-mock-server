package cn.shaikuba.mock.service.behavior.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageConverterAdapter {

    @Autowired
    private List<MessageConverter<String, String>> messageConverters;

    public String convert(MessageType messageType, String origin) {
        return messageConverters.stream().filter(messageConverter -> messageConverter.isSupport(messageType))
                .findFirst().get().convert(origin);
    }
}
