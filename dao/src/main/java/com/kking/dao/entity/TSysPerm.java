package com.kking.dao.entity;

import java.util.Date;
import java.util.List;

public class TSysPerm extends BaseEntity{
    public interface PERM_TYPE{
        final String MENU = "MENU";
    }
    private Integer id;
    private Date createTime;
    private Integer resourceId;
    private String permName;
    private Integer actionId;
    private String permType;
    private List<TSysAction> actionList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getPermType() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType = permType;
    }

    public List<TSysAction> getActionList() {
        return actionList;
    }

    public void setActionList(List<TSysAction> actionList) {
        this.actionList = actionList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", permName=").append(permName);
        sb.append(", actionId=").append(actionId);
        sb.append(", permType=").append(permType);
        sb.append("]");
        return sb.toString();
    }
}
