package com.my.common.system.domain;


 public class Role extends BasePO {
    private static final long serialVersionUID = 4767307740910429540L;
     
	private Long id;

    private String name;

    private String remark;
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
