/*
 * TUjuitService.java
 * Copyright(C) 2015 uju
 * All rights reserved.
 * -----------------------------------------------
 * 2018-04-09 Created
 */
package com.trade.usds.sdk.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 
 * 
 */
public class TServiceUjuit implements Serializable {

    /**
     * id
     */
    private Integer id;
    private Integer serviceChildId;
    /**
     * 服务名称
     */
    private String name;
    /**
     * 负责人
     */
    private Integer userId;
    /**
     * 服务器ID
     */
    private Integer serverId;
    /**
     * 端口
     */
    private String port;
    /**
     * 通知组ID
     */
    private Integer groupId;
    /**
     * 类型（1，股票2，期货，3公共部分）
     */
    private Short type;
    /**
     * 环境（0，开发环境 1.内网测试环境，2、内网生产换）
     */
    private Short environment;
    /**
     * 运行时间（0交易日1、每天、2非交易日）
     */
    private Short runType;
    /**
     * 超时时间单位分钟
     */
    private Integer outtime;
    /**
     * 访问路径
     */
    private String url;
    /**
     * 服务描述
     */
    private String description;
    /**
     * 应用指纹
     */
    private String appKey;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * update_time
     */
    private Date updateTime;
    
    /**
     * 服务状态0，正常，1警告中，2异常中
     */
    private Integer status;
    private Date lastStarTime;
    private String groupName;
 
    
    private List<TServiceSlot> list;
    
    
    public List<TServiceSlot> getList() {
		return list;
	}
	public void setList(List<TServiceSlot> list) {
		this.list = list;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
 
 
	private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getServerId() {
        return serverId;
    }
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }
    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public Short getType() {
        return type;
    }
    public void setType(Short type) {
        this.type = type;
    }
    public Short getEnvironment() {
        return environment;
    }
    public void setEnvironment(Short environment) {
        this.environment = environment;
    }
    public Short getRunType() {
        return runType;
    }
    public void setRunType(Short runType) {
        this.runType = runType;
    }
    public Integer getOuttime() {
        return outtime;
    }
    public void setOuttime(Integer outtime) {
        this.outtime = outtime;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    public String getAppKey() {
        return appKey;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLastStarTime() {
		return lastStarTime;
	}
	public void setLastStarTime(Date lastStarTime) {
		this.lastStarTime = lastStarTime;
	}
	public Integer getServiceChildId() {
		return serviceChildId;
	}
	public void setServiceChildId(Integer serviceChildId) {
		this.serviceChildId = serviceChildId;
	}
	 
    
    
}