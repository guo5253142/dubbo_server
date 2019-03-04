package com.my.common.common;

import java.io.Serializable;
import java.util.List;

public class DataPage<T>implements Serializable{
  private static final long serialVersionUID = -7021480900278637440L;
  public static final String ASC = "asc";
  public static final String DESC = "desc";
  private int page = 1;
  private int limit = 3;
  private boolean needData = true;
  private boolean needTotalCount = true;
  private List<T> dataList = null;
  private long totalCount = -1L;
  private boolean needPage=true;

  public DataPage()
  {
  }

  public DataPage(int limit) {
    this.limit = limit;
  }

  public DataPage(int page, int limit) {
    this.page = page;
    this.limit = limit;
  }

  public DataPage(boolean needData, boolean needTotalCount) {
    this.needData = needData;
    this.needTotalCount = needTotalCount;
  }

  public int getPage()
  {
    return this.page;
  }

  public void setPage(int page)
  {
    this.page = page;

    if (page < 1)
      this.page = 1;
  }

  public DataPage<T> page(int thePage)
  {
    setPage(thePage);
    return this;
  }

  public int getLimit()
  {
    return this.limit;
  }

  public void setLimit(int limit)
  {
    this.limit = limit;
  }

  public DataPage<T> pageSize(int limit)
  {
    setLimit(limit);
    return this;
  }

  public int getFirst()
  {
    return (this.page - 1) * this.limit;
  }

  public boolean isNeedTotalCount()
  {
    return this.needTotalCount;
  }

  public boolean isNeedData() {
    return this.needData;
  }

  public void setNeedData(boolean needData) {
    this.needData = needData;
  }

  public void setNeedTotalCount(boolean autoCount)
  {
    this.needTotalCount = autoCount;
  }

    public boolean isNeedPage() {
        return needPage;
    }

    public void setNeedPage(boolean needPage) {
        this.needPage = needPage;
    }

    public DataPage<T> needTotalCount(boolean theAutoCount)
  {
    setNeedTotalCount(theAutoCount);
    return this;
  }

  public List<T> getDataList()
  {
    return this.dataList;
  }

  public void setDataList(List<T> result)
  {
    this.dataList = result;
  }

  public long getTotalCount()
  {
    return this.totalCount;
  }

  public void setTotalCount(long totalCount)
  {
    this.totalCount = totalCount;
  }

  public long getTotalPages()
  {
    if (this.totalCount < 0L) {
      return -1L;
    }

    long count = this.totalCount / this.limit;
    if (this.totalCount % this.limit > 0L) {
      count += 1L;
    }
    return count;
  }

  public boolean isHasNext()
  {
    return this.page + 1 <= getTotalPages();
  }

  public int getNextPage()
  {
    if (isHasNext()) {
      return this.page + 1;
    }
    return this.page;
  }

  public int getEndIndex() {
    return this.page * this.limit;
  }

  public int getStartIndex() {
    return getFirst();
  }

  public boolean isHasPrev()
  {
    return this.page - 1 >= 1;
  }

  public int getPrevPage()
  {
    if (isHasPrev()) {
      return this.page - 1;
    }
    return this.page;
  }
}