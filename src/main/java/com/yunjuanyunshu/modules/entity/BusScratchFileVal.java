package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author carl
 * @since 2017-12-10
 */
@TableName("bus_scratch_file_val")
public class BusScratchFileVal extends Model<BusScratchFileVal> {

private static final long serialVersionUID = 1L;

                                private String id;
                private String content;

                    
    public String getId() {
            return id;
            }

                public void setId(String id) {
                    this.id = id;
                    }
                    
    public String getContent() {
            return content;
            }

                public void setContent(String content) {
                    this.content = content;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "BusScratchFileVal{" +
                            "id=" + id +
                                    ", content=" + content +
                    "}";
        }
        }
