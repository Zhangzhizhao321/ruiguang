package com.dream.common.core.util;

import java.util.Collection;
import java.util.Map;

import com.dream.common.core.exception.BizException;
import com.dream.common.core.exception.ErrorCode;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <p>
 * 业务断言<br>
 * 参考：org.junit.Assert
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-09-03
 */
public class Assert {

    protected Assert() {
        // to do noting
    }

    /**
     * 大于O
     */
    public static void gtZero(Integer num, ErrorCode errorCode) {
        if (num == null || num <= 0) {
            Assert.fail(errorCode);
        }
    }

    /**
     * 大于等于O
     */
    public static void geZero(Integer num, ErrorCode errorCode) {
        if (num == null || num < 0) {
            Assert.fail(errorCode);
        }
    }

    /**
     * num1大于num2
     */
    public static void gt(Integer num1, Integer num2, ErrorCode errorCode) {
        if (num1 <= num2) {
            Assert.fail(errorCode);
        }
    }

    /**
     * num1大于等于num2
     */
    public static void ge(Integer num1, Integer num2, ErrorCode errorCode) {
        if (num1 < num2) {
            Assert.fail(errorCode);
        }
    }

    /**
     * obj1 eq obj2
     */
    public static void eq(Object obj1, Object obj2, ErrorCode errorCode) {
        if (!obj1.equals(obj2)) {
            Assert.fail(errorCode);
        }
    }

    public static void isTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) {
            Assert.fail(errorCode);
        }
    }

    public static void isFalse(boolean condition, ErrorCode errorCode) {
        if (condition) {
            Assert.fail(errorCode);
        }
    }

    public static void isNull(ErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isNotNull(conditions)) {
            Assert.fail(errorCode);
        }
    }

    public static void notNull(ErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            Assert.fail(errorCode);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param errorCode 异常错误码
     */
    public static void fail(ErrorCode errorCode) {
        throw new BizException(errorCode);
    }

    public static void fail(boolean condition, ErrorCode errorCode) {
        if (condition) {
            Assert.fail(errorCode);
        }
    }

    public static void fail(String message) {
        throw new BizException(message);
    }

    public static void fail(boolean condition, String message) {
        if (condition) {
            Assert.fail(message);
        }
    }

    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            Assert.fail(errorCode);
        }
    }

    public static void noNullElements(Object[] array, ErrorCode errorCode) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    Assert.fail(errorCode);
                }
            }
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtil.isNotEmpty(collection)) {
            Assert.fail(errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (ObjectUtils.isEmpty(map)) {
            Assert.fail(errorCode);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, ErrorCode errorCode) {
        Assert.notNull(errorCode, type);
        if (!type.isInstance(obj)) {
            Assert.fail(errorCode);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, ErrorCode errorCode) {
        Assert.notNull(errorCode, superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            Assert.fail(errorCode);
        }
    }
}
