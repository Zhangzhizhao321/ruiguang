package com.dream.common.core.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class MomolepcPropUtil {

    private static String serverUrl;
    private static String fileServerPath;
    private static String urlPrefix;
    private static String mmlurl;
    private static String nettyurl;
    private static String mmlpcurl;
    private static String paimaiurl;
    private static String websocktUrl;

    static{
        ClassPathResource resource = new ClassPathResource("momole.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
            serverUrl=properties.getProperty("server.url");
            fileServerPath=properties.getProperty("fileServerPath");
            urlPrefix=properties.getProperty("urlPrefix");
            mmlurl=properties.getProperty("mmlurl");
            nettyurl=properties.getProperty("nettyurl");
			mmlpcurl=properties.getProperty("mmlpcurl");
			paimaiurl=properties.getProperty("paimaiurl");
            websocktUrl=properties.getProperty("websockt_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerUrl(){
        return serverUrl;
    }

    public static String getFileServerPath() {
        return fileServerPath;
    }

    public static String getUrlPrefix() {
        return urlPrefix;
    }

    public static String getMmlurl() {
        return mmlurl;
    }

    public static String getNettyurl() {
        return nettyurl;
    }

	public static String getMmlpcurl() {
		return mmlpcurl;
	}

	public static String getPaimaiurl(){return paimaiurl;}

    public static String getWebsocktUrl(){return websocktUrl;}

}
