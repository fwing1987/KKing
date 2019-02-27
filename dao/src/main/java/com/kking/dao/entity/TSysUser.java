package com.kking.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TSysUser extends BaseEntity{
    private Integer id;
    private String userName;
    private String password;
    private String nick;
    private String salt;
    private Date createTime;
    private Date updateTime;
    private Integer state;
    private String avatar;

    private List<TSysRole> roleList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<TSysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<TSysRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", nick=").append(nick);
        sb.append(", salt=").append(salt);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", state=").append(state);
        sb.append(", avatar=").append(avatar);
        sb.append("]");
        return sb.toString();
    }

    public interface STATE {
        int NORMAL = 0;
        int INVALID = 1;
    }
}
