package cn.shaikuba.mock.service.behavior.message;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class XmlMessageConverter implements MessageConverter<String, String> {

    @Override
    public boolean isSupport(MessageType messageType) {
        return messageType.equals(MessageType.XML);
    }

    @Override
    public String convert(String origin) {
        try {
            Document document = DocumentHelper.parseText(origin);

            if (document != null) {
                return document.asXML();
            }
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
