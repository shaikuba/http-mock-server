package cn.shaikuba.mock.data.mongodb.document;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "col.mock.cart")
public class CartDocument extends AbstractDocument {

    @Field("mockKey")
    private String mockKey;

    @Field("mockResponse")
    private JSONObject mockResponse;

}
