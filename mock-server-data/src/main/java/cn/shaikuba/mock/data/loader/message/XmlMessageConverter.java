package cn.shaikuba.mock.data.loader.message;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class XmlMessageConverter implements MessageConverter<String, Document> {

    @Override
    public boolean isSupport(String message) {
        return convert(message) != null;
    }

    @Override
    public Document convert(String origin) {
        try {
            Document document = DocumentHelper.parseText(origin);
            if (document != null) {
                return document;
            }
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public String format(String origin) {
        return convert(origin).asXML();
    }
}
