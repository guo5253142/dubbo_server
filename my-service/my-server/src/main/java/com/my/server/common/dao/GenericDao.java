package com.my.server.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;

import com.my.common.common.DataPage;


public abstract interface GenericDao
{
  public abstract long generateSequence(String paramString);

  public abstract <T> int insertAndSetupId(String paramString, T paramT);

  public abstract int update(String paramString, Map<String, Object> paramMap);
  public int updateByObj(String sqlNameWithNameSpace, Object param);
  public abstract <T> T queryUnique(String paramString, Map<String, Object> paramMap);

  public abstract Object queryObject(String paramString, Map<String, Object> paramMap);

  public abstract int queryCount(String paramString, Map<String, Object> paramMap);

  public abstract int queryInt(String paramString, Map<String, Object> paramMap);

  public abstract <T> List<T> queryList(String paramString, Map<String, Object> paramMap);

  public abstract <T> DataPage<T> queryPage(String paramString1, String paramString2, Map<String, Object> paramMap, DataPage<T> paramDataPage);

  public abstract <T> List<T> queryList(String paramString, Map<String, Object> paramMap, DataPage<T> paramDataPage);

  public abstract List<BatchResult> flushStatements();
}