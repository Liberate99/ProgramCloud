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
@TableName("oj_language")
public class OjLanguage extends Model<OjLanguage> {

private static final long serialVersionUID = 1L;

                                @TableId(value="id", type= IdType.AUTO)
                private Integer id;
                @TableField("language_slug")
        private String languageSlug;
                @TableField("language_name")
        private String languageName;
                @TableField("compile_command")
        private String compileCommand;
                @TableField("run_command")
        private String runCommand;

                    
    public Integer getId() {
            return id;
            }

                public void setId(Integer id) {
                    this.id = id;
                    }
                    
    public String getLanguageSlug() {
            return languageSlug;
            }

                public void setLanguageSlug(String languageSlug) {
                    this.languageSlug = languageSlug;
                    }
                    
    public String getLanguageName() {
            return languageName;
            }

                public void setLanguageName(String languageName) {
                    this.languageName = languageName;
                    }
                    
    public String getCompileCommand() {
            return compileCommand;
            }

                public void setCompileCommand(String compileCommand) {
                    this.compileCommand = compileCommand;
                    }
                    
    public String getRunCommand() {
            return runCommand;
            }

                public void setRunCommand(String runCommand) {
                    this.runCommand = runCommand;
                    }
    
@Override
protected Serializable pkVal() {
                return this.id;
            }

@Override
public String toString() {
        return "OjLanguage{" +
                            "id=" + id +
                                    ", languageSlug=" + languageSlug +
                                    ", languageName=" + languageName +
                                    ", compileCommand=" + compileCommand +
                                    ", runCommand=" + runCommand +
                    "}";
        }
        }
