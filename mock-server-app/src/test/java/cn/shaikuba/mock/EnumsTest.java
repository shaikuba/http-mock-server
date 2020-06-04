package cn.shaikuba.mock;

import cn.shaikuba.mock.common.process.entity.Enums;
import org.junit.Test;

/**
 */
public class EnumsTest
{
    @Test
    public void enumValueOf() {
        Enums.Partner.valueOf("EBU");
    }
}
