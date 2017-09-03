package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.BlogType;

/**
 * ��������Service�ӿ�
 *
 * @author Administrator
 */
public interface BlogTypeService {

    /**
     * ��ѯ���в������ͣ��Լ���Ӧ�Ĳ�������
     *
     * @return
     */
    public List<BlogType> countList();

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
