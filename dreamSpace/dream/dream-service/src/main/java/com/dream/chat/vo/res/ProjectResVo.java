package com.dream.chat.vo.res;

import com.dream.chat.vo.req.SupperReqVo;
import com.dream.common.core.util.TimeTools;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-10-31
 */
//@Data
@ApiModel(value = "项目", description = "项目")
public class ProjectResVo{

    private String Id;

    private String title;

    private String indexPic;

    private String categoryName;

    private String headPic;

    private String userName;

    private String phoneNumber;

    private String area;

    private BigDecimal nowAmount;

    private BigDecimal targetAmount;

    private BigDecimal helpCount;

    private Integer relationId;

    private BigDecimal percent;

    private Integer overDays;

    private Integer isOver;

    private String userId;

    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private String overTime;

    private String relation;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getIsOver() {
        return isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = TimeTools.format2(createTime);
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = TimeTools.format2(overTime);
    }

    public Integer getOverDays() {
        return overDays;
    }

    public void setOverDays(Integer overDays) {
        this.overDays = overDays;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndexPic() {
        return indexPic;
    }

    public void setIndexPic(String indexPic) {
        this.indexPic = indexPic;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area.split("-")[0];
    }

    public BigDecimal getNowAmount() {
        return nowAmount;
    }

    public void setNowAmount(BigDecimal nowAmount) {
        this.nowAmount = nowAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getHelpCount() {
        return helpCount;
    }

    public void setHelpCount(BigDecimal helpCount) {
        this.helpCount = helpCount;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
