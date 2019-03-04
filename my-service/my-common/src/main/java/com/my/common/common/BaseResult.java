package com.my.common.common;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -7228469805686712268L;
	private String errorMsg;
	private String errorCode;
	private String errorDetailStack;
	private boolean isSuccess = true;

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		this.isSuccess = false;
	}
	
	//成功需要提示信息返回给页面
	public void setSuccessMsg(String successMsg) {
		this.errorMsg = successMsg;
	}
	
	public String getSuccessMsg() {
		return this.errorMsg;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetailStack() {
		return this.errorDetailStack;
	}

	public void setErrorDetailStack(String errorDetailStack) {
		this.errorDetailStack = errorDetailStack;
		this.isSuccess = false;
	}

	public boolean isSuccess() {
		return this.isSuccess;
	}

	public BaseResult<T> withError(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.isSuccess = false;
		return this;
	}

	/*public BaseResult<T> withError(SinafenqiExceptionCode codeObj) {
		if (codeObj != null) {
			this.errorCode = codeObj.getCode();
			this.errorMsg = codeObj.getMsg();
		}
		this.isSuccess = false;
		return this;
	}*/
}