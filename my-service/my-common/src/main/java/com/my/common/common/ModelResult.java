package com.my.common.common;

public class ModelResult<T> extends BaseResult<T>{
  private static final long serialVersionUID = -6516149422706135072L;
  private T result;

  public T getResult()
  {
    return this.result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}