package com.yunjuanyunshu.modules.entity;

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
 * @author xjz
 * @since 2017-06-28
 */
@TableName("sys_role_api")
public class RoleApi extends Model<RoleApi> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId("role_id")
	private String roleId;
    /**
     * 菜单编号
     */
	@TableField("api_tree_id")
	private String apiTreeId;
	/**
	 * 使用状态
	 */
	@TableField("status")
	private String status;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getApiTreeId() {
		return apiTreeId;
	}

	public void setApiTreeId(String apiTreeId) {
		this.apiTreeId = apiTreeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
