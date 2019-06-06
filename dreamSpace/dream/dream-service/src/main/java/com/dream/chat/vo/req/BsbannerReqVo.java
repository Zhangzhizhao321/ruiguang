package com.dream.chat.vo.req;

import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BsbannerReqVo {

    private MultipartFile file;

    private Integer sort;

    private Integer status;
    
}
