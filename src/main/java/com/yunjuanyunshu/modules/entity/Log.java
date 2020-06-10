package com.yunjuanyunshu.modules.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjz
 * @since 2017-06-28
 */
@TableName("sys_log")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	private String id;
    /**
     * 日志类型
     */
	private String type;
    /**
     * 日志标题
     */
	private String title;
    /**
     * 创建者
     */
	@TableField("create_by")
	private String createBy;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 操作IP地址
     */
	@TableField("remote_addr")
	private String remoteAddr;
    /**
     * 用户代理
     */
	@TableField("user_agent")
	private String userAgent;
    /**
     * 请求URI
     */
	@TableField("request_uri")
	private String requestUri;
    /**
     * 操作方式
     */
	private String method;
    /**
     * 操作提交的数据
     */
	private String params;
    /**
     * 异常信息
     */
	private String exception;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
