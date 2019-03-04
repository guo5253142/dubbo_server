package com.my.common.common;

public class PageResult<T> extends BaseResult<T>
{
  private static final long serialVersionUID = 1400532352709932863L;
  private DataPage<T> page;

  public DataPage<T> getPage()
  {
    return this.page;
  }

  public void setPage(DataPage<T> page) {
    this.page = page;
  }
}