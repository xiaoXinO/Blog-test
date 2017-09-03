package com.java1234.service;

import com.java1234.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * ����Service�ӿ�
 *
 * @author Administrator
 */
public interface CommentService {

    /**
     * ��ȡComment����
     *
     * @param map
     * @return
     */
    List<Comment> list(Map<String, Object> map);

    /**
     * ��ȡ�ܼ�¼��
     *
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * �������
     *
     * @param comment
     * @return
     */
    Integer add(Comment comment);
    /**
     * �޸�����
     * @param comment
     * @return
     */
    Integer update(Comment comment);
    /**
     * ɾ������
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
