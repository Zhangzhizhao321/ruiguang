package com.dream.common.core.exception;

/**
 * <p>
 * 错误码接口
 * 异常码由4位模块编号 + 4位错误码组成,例如 [100010002]，唯一 <br>
 * <pre>
 *      用户服务异常 1001
 *      账户服务异常  1002
 *      支付网关异常 1003
 *  <pre/>
 *
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-09-03
 */
public interface ErrorCode {

    /**
     * 错误编码
     */
    long getCode();

    /**
     * 错误描述
     */
    String getMsg();

}
