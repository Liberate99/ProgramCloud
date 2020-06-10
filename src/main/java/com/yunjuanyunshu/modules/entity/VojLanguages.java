package com.yunjuanyunshu.modules.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
@TableName("voj_languages")
public class VojLanguages extends Model<VojLanguages> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "language_id", type = IdType.AUTO)
    private Integer languageId;
    @TableField("language_slug")
    private String languageSlug;
    @TableField("language_name")
    private String languageName;
    @TableField("language_compile_command")
    private String languageCompileCommand;
    @TableField("language_run_command")
    private String languageRunCommand;


    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
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

    public String getLanguageCompileCommand() {
        return languageCompileCommand;
    }

    public void setLanguageCompileCommand(String languageCompileCommand) {
        this.languageCompileCommand = languageCompileCommand;
    }

    public String getLanguageRunCommand() {
        return languageRunCommand;
    }

    public void setLanguageRunCommand(String languageRunCommand) {
        this.languageRunCommand = languageRunCommand;
    }

    @Override
    protected Serializable pkVal() {
        return this.languageId;
    }

    @Override
    public String toString() {
        return "VojLanguages{" +
                "languageId=" + languageId +
                ", languageSlug=" + languageSlug +
                ", languageName=" + languageName +
                ", languageCompileCommand=" + languageCompileCommand +
                ", languageRunCommand=" + languageRunCommand +
                "}";
    }
}
