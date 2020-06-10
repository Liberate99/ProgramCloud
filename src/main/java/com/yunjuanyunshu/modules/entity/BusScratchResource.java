package com.yunjuanyunshu.modules.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
@TableName("bus_scratch_resource")
public class BusScratchResource extends Model<BusScratchResource> {

private static final long serialVersionUID = 1L;

                                private String id;
            /**
     * 名称
     */
            private String name;
            /**
     * 内容
     */
            private String content;
            /**
     * 1是背景，2是角色，3是造型，4是声音
     */
            private String type;
            /**
     * 排序
     */
            private Integer sort;
            /**
     * 删除标记
     */
            @TableField("del_flag")
        private String delFlag;
            /**
     * 创建者
     */
            @TableField("create_by")
        private String createBy;
            /**
     * 更新者
     */
            @TableField("update_by")
        private String updateBy;
            /**
     * 创建时间
     */
            @TableField("create_date")
        private Date createDate;
            /**
     * 更新时间
     */
            @TableField("update_date")
        private Date updateDate;

                    
    public String getId() {
            return id;
            }

                public void setId(String id) {
                    this.id = id;
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
                    
    public String getType() {
            return type;
            }

                public void setType(String type) {
                    this.type = type;
                    }
                    
    public Integer getSort() {
            return sort;
            }

                public void setSort(Integer sort) {
                    this.sort = sort;
                    }
                    
    public String getDelFlag() {
            return delFlag;
            }

                public void setDelFlag(String delFlag) {
                    this.delFlag = delFlag;
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
                    
    public Date getCreateDate() {
            return createDate;
            }

                public void setCreateDate(Date createDate) {
                    this.createDate = createDate;
                    }
                    
    public Date getUpdateDate() {
            return updateDate;
            }

                public void setUpdateDate(Date updateDate) {
                    this.updateDate = updateDate;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "BusScratchResource{" +
                            "id=" + id +
                                    ", name=" + name +
                                    ", content=" + content +
                                    ", type=" + type +
                                    ", sort=" + sort +
                                    ", delFlag=" + delFlag +
                                    ", createBy=" + createBy +
                                    ", updateBy=" + updateBy +
                                    ", createDate=" + createDate +
                                    ", updateDate=" + updateDate +
                    "}";
        }
        }
