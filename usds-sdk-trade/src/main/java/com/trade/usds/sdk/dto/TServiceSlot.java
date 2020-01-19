/*
 * TSlotService.java
 * Copyright(C) 2015 uju
 * All rights reserved.
 * -----------------------------------------------
 * 2018-04-09 Created
 */
package com.trade.usds.sdk.dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @Description 
 * 
 */
public class TServiceSlot implements Serializable {

    /**
     * id
     */
    private Integer id;
    /**
     * 开始时间
     */
    private Time startTime;
    /**
     * 结束时间
     */
    private Time endTime;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * 服务ID
     */
    private Integer serviceId;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = Time.valueOf(startTime);
    }
    
    public void setEndTime(String endTime) {
        this.endTime = Time.valueOf(endTime);
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
 
 
}