package com.dream.common.core.util;

import cn.hutool.setting.dialect.Props;
import org.springframework.util.StringUtils;

public class AlipayConfig {

	private static Props props;

	static {
		props = new Props("momole.properties");
	}
    public static String BASE_URL = null;
    // 商户appid
    public static String APPID = "2018011101771401";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+gHJg5mlAs+VLM11X29G9t4CvGSo0fy8Bfokrag9lo/dRPEst3dNxKjKqxQ9wYJydxtYvzZDN28p1Rp1VEAbaC2zXs6O+dFAkfAJw2L2hDhumlzL+bTHvVDHEZQMyoqJbVd1wiHDAsShpy7+6K23qDFmNHHHvTa0jQYx7GBF6yXsptQcZfxSkPVYaOCUEdmnM+jWYcmcK3tZPJSLafnJIsTjmKNSYYm48SMtlE0aiLxoEprOe8Utze/BjhV71HQrJa6A+SGbazq1Euq/2YDeyDTEqkZv31g9jZTWrYjcSL9ycATSQ492PyuVkGN+IV+0qbBI3TBQa9Ui4jSsII6CLAgMBAAECggEBAIDXFoR60Qy1nopxvtvBC+WnyhyihGzk58G29saqAKfZcVc71Ay9TlfnFkVw6bQzYzlCVXIXMaxIznDqxVXWdQvbVk28bS5ONDW4uV3g+L+IbyMId7Cj4XXgF5igSL1iSTREqnOFk9x+9Lo41v7uiHQWziol5tjWTBbgKkbGTGwh06WoWfGzbBwjQgwctf99/81iZkR8IaISpWvNv8XS552rLYtjTr16uBbbGKjSjc36TqpnVuLFIn++ur0YtEzfrqcaGi1YB8H8lxHnwbejgIT+Z+V+vo95KxVgAHJpY8RRfOHIhYLS4RmllvQh5XtVjyeLr5owMdu01E3Pc2ejtaECgYEA+GdfoZFH8Gd7+BoJnWyx5gYMEe0QHH2yVc7TWa3/67dy/9b6VZf9firywoKKBNMJV/dqz8Q/PFqlELsnd0nv9KHWzD7RgxnpDl6J7S1ycluab+FsP9R2aVqoH/n4NyRjxkc3uUegboXxL1bVNAlfv8g7HUvqAWjR0/3G0HTUmF0CgYEAxFPJoM0xH9mx7AoH1/0eaTxZGz5TZqGqC2FGPKqMubaVdn9XAXsDtoKREI4xbcsggo/Gc7mitiWbpbekDzv7vfqejANs4T8sfk9iKAwO1Gu7Ym1PMst4Qtzw/FvO7CSonifYJ+O5pnVmkHQ0Y8dDVrsYxfDn4hDAUpd/oTSR7gcCgYA52eFkwHAPphoyEuZQWoMn/hqLhjltWTzSTSPdy+9BV20Z9lhA3O/1lU0KTdqU+w0/BmlC0AKmLLoqpluvpXGT+KkM0kh7Duq/JPdcXGw+A+V4zvgl+XZGXQ2wvMe5F+mAwQ9SQEM12k3sZ32p69D32ksiLI7oGyBbDcxiugLVJQKBgBKXVJqTk7lOZhjSAlgjsgOJIW8BAejQGkgll/8ar2TOs2ybNmP/gCSCTLZioK+i8psqfkJrK8tP2GNi9ngy1+3vk5/ymxbhYpc7cTIVojnfUclKTJukxOif/3oGP5wBBK6lW0S7vdLTfzjiqCtirS/DW98ypuKzb2Li0cU0MzV1AoGAApMgz2NQ3tgOhEL+7PCS49tNMCPV/szgkKNYEQS4N/Kq3NUzM1cBhjnwN2tvaguHkrLJOdUDyiLotuREo/FcRz2aSIwF+U4ENq6X1D75faMhxZmciynjs+IxQkkqqSUy/BLJRcXv4pvjOq3sFWMrtZz6YvnvmytQ+HIz0QDL6fI=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //public static String notify_url = "http://anmoyi.dream.com.cn/dream_test/notify/alipayNotify.json";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    //public static String return_url = "http://anmoyi.dream.com.cn/dream_test/resources/html/chair/time.html";
    //public static String notify_url = "http://io.dream.com.cn/dream_test/notify/alipayNotify.json";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    //public static String return_url = "http://io.dream.com.cn/dream_test/resources/html/chair/time.html";
    //public static String notify_url = "http://io.dream.com.cn/dream/notify/alipayNotify.json";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    //public static String return_url = "http://io.dream.com.cn/dream/resources/html/chair/time.html";
	//public static String notify_url = genUrlPrefix()+"/notify/v1/rechargeAli";//props.getStr("notify_url");
	public static String notify_url =props.getStr("paimaiurl")+"/notify/v1/rechargeAli";//props.getStr("notify_url");
	public static String return_url = props.getStr("return_url");
	//public static String notify_url = "http://anmoyi.dream.com.cn/dream_test/notify/alipayNotify.json";
	//public static String notify_url = "http://anmoyi.dream.com.cn/dream/notify/alipayNotify.json";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	// public static String return_url = "http://anmoyi.dream.com.cn/dream_test/resources/html/chair/alibackstore.html";
	//public static String return_url = "http://anmoyi.dream.com.cn/dream/resources/html/chair/alibackstore.html";
    // 请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgG4Qik1PJ0L+N00a3K87MxaEPtWSly1a+iPzx5ZojSLqFdbEAN0nxg54bncB04ssbPV9fQFg0GTYd+RU+RQiSow02bbBc4lpsnclrH3UKs1fHrPVuw6e0VAEupAmfs/qykjE03tisoNM8iQh+AenlBgo+4+DckizU6Pw6bVOcwpggMq+L1CWixhYo4ISOaKGe7uWy76dVPkYVPBXGPA7G87WcZIkWfVSK+C/oISoErdSpkg8bMrvkqwPbRZaH2+MIwhNsQHut/tife9zP4EDwBFv/cupRkWx0v898pq0Ir3X/LPskDz93ykftjFkuzT4vz2OxrwzgBe/98aJGZ3XVQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

    public static String noifyUrl(String url){
        return props.getStr("paimaiurl")+url;
    }

    private static String genUrlPrefix() {
        if (StringUtils.isEmpty(BASE_URL)) {
            BASE_URL = MomolepcPropUtil.getPaimaiurl();
        }
        return BASE_URL;
    }
}
