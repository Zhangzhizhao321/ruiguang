package com.dream.chat.vo.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class BsProCategoryReqVo {

    private String categoryName;

    private String categoryNote;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;
    
}
