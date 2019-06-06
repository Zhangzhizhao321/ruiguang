package com.dream.chat.utils;

import cn.hutool.setting.dialect.Props;
import com.dream.common.core.util.ImageUtil;
import com.dream.common.core.util.MathUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-20
 */
public class CommonUtil {
    private static Props props;

    static {
        props = new Props("momole.properties");
    }

    public static String upload(String url, MultipartFile img, HttpServletRequest request) {
        String photoName = MathUtil.getUUID() + ".jpg";
        // String filePath = request.getSession().getServletContext().getRealPath("img/"+url+"/");
        //TODO 图片路径
        String filePath = "F://img/";
        //String filePath = props.getStr("upload_url") + "/" + url + "/";
        File fileSourcePath = new File(filePath);
        File fileSource = new File(fileSourcePath, photoName);
        if (!fileSourcePath.exists()) {
            fileSourcePath.mkdirs();
        }
        String ex = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));

        try {
            int angle = ImageUtil.getAngle(ImageUtil.getExif(img.getInputStream()));
            BufferedImage bf = ImageUtil.getBufferedImg(ImageIO.read(img.getInputStream()), ImageUtil.getWidth(img.getInputStream()), ImageUtil.getHeight(img.getInputStream()), angle);
            ImageIO.write(bf, ex.substring(1), fileSource);
            //img.transferTo(fileSource);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(props.getStr("watch_url") + "/" + url + "/" + photoName);
        return  /*props.getStr("watch_url")+*/"/" + url + "/" + photoName;
    }
}
