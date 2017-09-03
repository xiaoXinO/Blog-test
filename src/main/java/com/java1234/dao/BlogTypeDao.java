package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.BlogType;
import org.omg.CORBA.INTERNAL;

/**
 * ��������Dao�ӿ�
 *
 * @author Administrator
 */
public interface BlogTypeDao {

    /**
     * ��ѯ���в������ͣ��Լ���Ӧ�Ĳ�������
     *
     * @return
     */
    List<BlogType> countList();

    /**
     * ͨ��id���Ҳ�������ʵ��
     *
     * @param id
     * @return
     */
    BlogType findById(Integer id);

    /**
     * ��ȡBlogType����
     *
     * @return
     */
    List<BlogType> list(Map<String, Object> map);

    /**
     * ��ȡ�ܼ�¼��
     *
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * ��Ӳ������
     *
     * @param blogType
     * @return
     */
    Integer add(BlogType blogType);

    /**
     * ����idɾ���������
     *
     * @param blogTypeId
     */
    Integer delete(Integer blogTypeId);

    /**
     * ����id�޸Ĳ������
     *
     * @param blogType
     * @return
     */
    Integer update(BlogType blogType);
}
