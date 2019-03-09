package com.kking.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TSysRole extends BaseEntity{
    public interface PERM_TYPE{
        String ALL = "A";//所有权限
        String PART = "P";//部分权限
    }
    private Integer id;
    private String roleName;
    private String roleDesc;
    private Date createTime;
    private Integer state;
    private String deptPermType;
    private List<TSysPerm> permList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<TSysPerm> getPermList() {
        return permList;
    }

    public void setPermList(List<TSysPerm> permList) {
        this.permList = permList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDeptPermType() {
        return deptPermType;
    }

    public void setDeptPermType(String deptPermType) {
        this.deptPermType = deptPermType;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleDesc=").append(roleDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", state=").append(state);
        sb.append(", deptPermType=").append(deptPermType);
        sb.append("]");
        return sb.toString();
    }
}
