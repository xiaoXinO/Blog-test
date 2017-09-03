package com.java1234.dao;

import com.java1234.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论Dao接口
 *
 * @author Administrator
 */
public interface CommentDao {

    /**
     * 获取所有Comment
     *
     * @param map
     * @return
     */
    List<Comment> list(Map<String, Object> map);

    /**
     * 获取总记录数
     *
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    Integer add(Comment comment);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    Integer update(Comment comment);

    /**
     * 删除评论
     * @param id
     * @return
     */
    Integer delete(Integer id);

}
