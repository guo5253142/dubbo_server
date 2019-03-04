package com.my.common.system.domain;

import java.io.Serializable;
import java.util.Date;

public class SysJobLog implements Serializable{

	private static final long serialVersionUID = 9098522849017638284L;

	/** 任务执行类名名称 */
    private String excuteClassName;
    
    /** 执行开始时间 */
    private Date beginTime;
    
    /** 执行结束时间 */
    private Date endTime;
    
    /** 总共执行的时间（毫秒） */
    private Long runMillisecond;

	public String getExcuteClassName() {
		return excuteClassName;
	}

	public SysJobLog() {
		super();
	}

	public SysJobLog(String excuteClassName, Date beginTime, Date endTime, Long runMillisecond) {
		super();
		this.excuteClassName = excuteClassName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.runMillisecond = runMillisecond;
	}

	@Override
	public String toString() {
		return "SysJobLog [excuteClassName=" + excuteClassName + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", runMillisecond=" + runMillisecond + "]";
	}

	public void setExcuteClassName(String excuteClassName) {
		this.excuteClassName = excuteClassName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getRunMillisecond() {
		return runMillisecond;
	}

	public void setRunMillisecond(Long runMillisecond) {
		this.runMillisecond = runMillisecond;
	}
    
    
}
