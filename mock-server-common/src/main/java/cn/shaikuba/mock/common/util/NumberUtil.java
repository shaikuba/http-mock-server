package cn.shaikuba.mock.common.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * NumberUtil
 *
 * @author Ray.Xu
 */
public final class NumberUtil {

    private NumberUtil() {}

    /**
     * Generate random number by the min value and max value.
     * @param min included min value.
     * @param max included max value.
     * @return
     */
    public static int random(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    public static String numberFormat(String pattern, int num) {
        return new DecimalFormat(pattern).format(num);
    }

    /**
     * Format number with the default pattern.
     * @param num
     * @return
     */
    public static String numberFormat(int num) {
        return new DecimalFormat("000000").format(num);
    }

    public static String numberFormat(String s) {
        return numberFormat(Integer.parseInt(s));
    }

}
