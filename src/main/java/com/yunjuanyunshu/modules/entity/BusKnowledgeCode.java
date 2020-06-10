package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gao
 * @since 2017-10-15
 */
@TableName("bus_knowledge_code")
public class BusKnowledgeCode extends Model<BusKnowledgeCode> {

private static final long serialVersionUID = 1L;

                /**
     * id
     */
                        private String id;
            /**
     * 内容
     */
            private String content;
            /**
     * 名称
     */
            private String name;
            /**
     * 代码
     */
            private String code;

            @TableField("chapter_id")
            private String chapterId;
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
                    
    public String getName() {
            return name;
            }

                public void setName(String name) {
                    this.name = name;
                    }
                    
    public String getCode() {
            return code;
            }

                public void setCode(String code) {
                    this.code = code;
                    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "BusKnowledgeCode{" +
                            "id=" + id +
                                    ", content=" + content +
                                    ", name=" + name +
                                    ", code=" + code +
                    "}";
        }
        }
