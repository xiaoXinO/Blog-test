package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Link;

/**
 * ��������Dao�ӿ�
 * @author Administrator
 *
 */
public interface LinkDao {

	/**
	 * ��������������Ϣ
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String, Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * �������������Ϣ
	 * @param blogType
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * �޸�����������Ϣ
	 * @param blogType
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * ɾ������������Ϣ
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
