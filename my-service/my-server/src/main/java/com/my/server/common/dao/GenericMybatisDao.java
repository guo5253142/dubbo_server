package com.my.server.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.my.common.common.DataPage;


public class GenericMybatisDao implements GenericDao {
	private static final Logger log = LoggerFactory.getLogger(GenericMybatisDao.class);
	private SqlSessionTemplate mybatisTemplate;

	public void setMybatisTemplate(SqlSessionTemplate mybatisTemplate) {
		this.mybatisTemplate = mybatisTemplate;
	}

	public long generateSequence(String sqlNameWithNameSpace) {
		return ((Long) this.mybatisTemplate.selectOne(sqlNameWithNameSpace))
				.longValue();
	}

	public <T> int insertAndSetupId(String sqlNameWithNameSpace, T obj) {
		return this.mybatisTemplate.insert(sqlNameWithNameSpace, obj);
	}

	public int update(String sqlNameWithNameSpace, Map<String, Object> param) {
		return this.mybatisTemplate.update(sqlNameWithNameSpace, param);
	}

	public Object queryObject(String sqlNameWithNameSpace,
			Map<String, Object> map) {
		return this.mybatisTemplate.selectOne(sqlNameWithNameSpace, map);
	}

	public <T> T queryUnique(String sqlNameWithNameSpace,
			Map<String, Object> map) {
		return this.mybatisTemplate.selectOne(sqlNameWithNameSpace, map);
	}

	public int queryCount(String sqlNameWithNameSpace, Map<String, Object> map) {
		return ((Integer) this.mybatisTemplate.selectOne(sqlNameWithNameSpace,
				map)).intValue();
	}

	public int queryInt(String sqlNameWithNameSpace, Map<String, Object> map) {
		return ((Integer) this.mybatisTemplate.selectOne(sqlNameWithNameSpace,
				map)).intValue();
	}

	public Long queryLong(String sqlNameWithNameSpace, Map<String, Object> map) {
		return ((Long) this.mybatisTemplate.selectOne(sqlNameWithNameSpace,
				map));
	}
	public Double queryDouble(String sqlNameWithNameSpace, Map<String, Object> map) {
		return ((Double) this.mybatisTemplate.selectOne(sqlNameWithNameSpace,
				map));
	}
	public <T> List<T> queryList(String sqlNameWithNameSpace,
			Map<String, Object> map, DataPage<T> page) {
		map.put("startIndex", Integer.valueOf(page.getFirst()));
		map.put("endIndex", Integer.valueOf(page.getEndIndex()));
		map.put("pageSize", Integer.valueOf(page.getLimit()));
		return queryList(sqlNameWithNameSpace, map);
	}

	public <T> List<T> queryList(String sqlNameWithNameSpace,
			Map<String, Object> map) {
		return this.mybatisTemplate.selectList(sqlNameWithNameSpace, map);
	}
	public <T> List<T> queryListByObj(String sqlNameWithNameSpace,
			Object obj) {
		return this.mybatisTemplate.selectList(sqlNameWithNameSpace, obj);
	}
	public <T> DataPage<T> queryPage(String countSqlNameWithNameSpace,
			String rsSqlNameWithNameSpace, Map<String, Object> paramMap,
			DataPage<T> page) {
		if (page.isNeedTotalCount()) {
			int count = queryCount(countSqlNameWithNameSpace, paramMap);
			page.setTotalCount(count);
		}

		if (page.isNeedData()) {
			if (page.isNeedTotalCount()) {
				if (page.getTotalCount() > 0L) {
					List resultList = queryList(rsSqlNameWithNameSpace,
							paramMap, page);
					page.setDataList(resultList);
				} else {
					page.setDataList(new ArrayList());
				}
			}

			if (!page.isNeedTotalCount()) {
				List resultList = queryList(rsSqlNameWithNameSpace, paramMap,
						page);
				page.setDataList(resultList);
			}

			if (!page.isNeedPage()) {
				paramMap.put("isNeedPage",false);
				List resultList = queryList(rsSqlNameWithNameSpace, paramMap,
						page);
				page.setDataList(resultList);
			}
		}
		return page;
	}

	public List<BatchResult> flushStatements() {
		return this.mybatisTemplate.flushStatements();
	}

	public int updateByObj(String sqlNameWithNameSpace, Object param) {
		return this.mybatisTemplate.update(sqlNameWithNameSpace, param);
	}
	
	public <T> T queryObject(String sqlNameWithNameSpace, Object param) {
		return this.mybatisTemplate.selectOne(sqlNameWithNameSpace, param);
	}
	
	public int queryInt(String sqlNameWithNameSpace, Object param) {
		return ((Integer) this.mybatisTemplate.selectOne(sqlNameWithNameSpace, param)).intValue();
	}
}