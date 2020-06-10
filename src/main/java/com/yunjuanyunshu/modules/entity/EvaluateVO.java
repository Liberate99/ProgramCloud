package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评价VO
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
public class EvaluateVO   {

    private static final long serialVersionUID = 1L;

    /**
     * title
     */
    private String title;
    /**
     * 通过次数
     */
    private Integer passTime;
    /**
     * 不同过次数
     */
    private Integer unPassTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    public Integer getUnPasstime() {
        return unPassTime;
    }

    public void setUnPasstime(Integer unPassTime) {
        this.unPassTime = unPassTime;
    }
}
