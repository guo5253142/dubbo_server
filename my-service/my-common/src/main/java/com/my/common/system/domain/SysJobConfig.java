package com.my.common.system.domain;

import java.io.Serializable;
import java.util.Date;

public class SysJobConfig implements Serializable {
	private static final long serialVersionUID = 3624255174660991613L;

	/** 任务名称 */
    private String name;

    /** 任务执行类名名称 */
    private String excuteClassName;

    /** 定时任务cron表达试 */
    private String cron;

    /** 任务联系人 */
    private String owner;

    /** 任务联系人电话 */
    private String ownerPhone;

    /** 创建人 */
    private Long creator;

    /** 创建时间 */
    private Date createDate;

    /** 修改人 */
    private Long editor;

    /** 修改时间 */
    private Date editDate;

    /** 启用标志 */
    private Integer usedTag;

    /** 备注 */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getExcuteClassName() {
        return excuteClassName;
    }

    public void setExcuteClassName(String excuteClassName) {
        this.excuteClassName = excuteClassName == null ? null : excuteClassName.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone == null ? null : ownerPhone.trim();
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getEditor() {
        return editor;
    }

    public void setEditor(Long editor) {
        this.editor = editor;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Integer getUsedTag() {
        return usedTag;
    }

    public void setUsedTag(Integer usedTag) {
        this.usedTag = usedTag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}