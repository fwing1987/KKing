package com.kking.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TSysRole extends BaseEntity{
    private Integer id;
    private String roleName;
    private Date createTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleName=").append(roleName);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
