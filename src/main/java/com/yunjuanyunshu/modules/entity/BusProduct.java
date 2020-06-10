package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author xjz
 * @since 2017-07-27
 */
@TableName("bus_product")
public class BusProduct extends Model<BusProduct> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("user_id")
    private String userId;
    private String product;
    private String pic;
    private String desc;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_by")
    private String createBy;
    private transient String username;
    private transient String photo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    //    private String createBy;
//    private String createBy;
    @TableField("del_flag")
    private String delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusProduct{" +
                "id=" + id +
                ", userId=" + userId +
                ", product=" + product +
                ", pic=" + pic +
                ", desc=" + desc +
                ", createTime=" + createTime +
                ", createBy=" + createBy +
                ", delFlag=" + delFlag +
                "}";
    }
}
