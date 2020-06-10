package com.yunjuanyunshu.modules.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * Scratch作品表
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
@TableName("bus_scratch_file")
public class BusScratchFile extends Model<BusScratchFile> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * 描述
     */
    private String description;
    private Integer hits;
    private String name;
    private String content;
    /**
     * 版本
     */
    private String version;
    private String status;
    /**
     * 作品类型，1是自由作品，2是课程作业
     */
    private String type;
    @TableField("create_by")
    private String createBy;
    @TableField("update_by")
    private String updateBy;
    @TableField("create_date")
    private Date createDate;
    @TableField("update_date")
    private Date updateDate;
    private String remarks;
    @TableField("del_flag")
    private String delFlag;
    /**
     * 封面
     */
    private String cover;
    /**
     * 章节id 可选
     */
    @TableField("chapter_id")
    private String chapterId;
    /**
     * 浏览量
     */
    private Integer scan;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getScan() {
        return scan;
    }

    public void setScan(Integer scan) {
        this.scan = scan;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BusScratchFile{" +
                "id=" + id +
                ", description=" + description +
                ", hits=" + hits +
                ", name=" + name +
                ", version=" + version +
                ", status=" + status +
                ", type=" + type +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", remarks=" + remarks +
                ", delFlag=" + delFlag +
                ", cover=" + cover +
                ", chapterId=" + chapterId +
                ", scan=" + scan +
                "}";
    }
}
