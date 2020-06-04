package cn.shaikuba.mock.data.entity.base;

import cn.shaikuba.mock.data.entity.api.BaseGson;
import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.Date;

@Data
public abstract class BaseEntity<M> extends Entity<M> implements BaseGson {

    @Expose
    private Date createTime;

    @Expose
    private Date updateTime;

}
