package com.kking.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class DnChannel implements Serializable {
    private Integer id;
    private Integer rootId;
    private Integer pid;
    private String name;
    private Integer level;
    private String channelCode;
    private String username;
    private String token;
    private String phone;
    private String password;
    private String province;
    private String city;
    private String country;
    private Integer platformFee;
    private Integer accType;
    private Integer ctype;
    private String isRedPack;
    private String payMethod;
    private Integer state;
    private Date updTime;
    private Date createTime;
    private String taobao;
    private Integer wxId;
    private String pids;
    private Integer isTop;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(Integer platformFee) {
        this.platformFee = platformFee;
    }
    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }
    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }
    public String getIsRedPack() {
        return isRedPack;
    }

    public void setIsRedPack(String isRedPack) {
        this.isRedPack = isRedPack;
    }
    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getTaobao() {
        return taobao;
    }

    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }
    public Integer getWxId() {
        return wxId;
    }

    public void setWxId(Integer wxId) {
        this.wxId = wxId;
    }
    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }
    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rootId=").append(rootId);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", level=").append(level);
        sb.append(", channelCode=").append(channelCode);
        sb.append(", username=").append(username);
        sb.append(", token=").append(token);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", country=").append(country);
        sb.append(", platformFee=").append(platformFee);
        sb.append(", accType=").append(accType);
        sb.append(", ctype=").append(ctype);
        sb.append(", isRedPack=").append(isRedPack);
        sb.append(", payMethod=").append(payMethod);
        sb.append(", state=").append(state);
        sb.append(", updTime=").append(updTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", taobao=").append(taobao);
        sb.append(", wxId=").append(wxId);
        sb.append(", pids=").append(pids);
        sb.append(", isTop=").append(isTop);
        sb.append("]");
        return sb.toString();
    }
}
