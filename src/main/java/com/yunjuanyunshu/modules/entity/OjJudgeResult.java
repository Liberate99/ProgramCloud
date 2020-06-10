package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@TableName("oj_judge_result")
public class OjJudgeResult extends Model<OjJudgeResult> {

private static final long serialVersionUID = 1L;

                                @TableId(value="id", type= IdType.AUTO)
                private Integer id;
                @TableField("result_slug")
        private String resultSlug;
                @TableField("result_name")
        private String resultName;

                    
    public Integer getId() {
            return id;
            }

                public void setId(Integer id) {
                    this.id = id;
                    }
                    
    public String getResultSlug() {
            return resultSlug;
            }

                public void setResultSlug(String resultSlug) {
                    this.resultSlug = resultSlug;
                    }
                    
    public String getResultName() {
            return resultName;
            }

                public void setResultName(String resultName) {
                    this.resultName = resultName;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "OjJudgeResult{" +
                            "id=" + id +
                                    ", resultSlug=" + resultSlug +
                                    ", resultName=" + resultName +
                    "}";
        }
        }
