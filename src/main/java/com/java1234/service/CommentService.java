package com.java1234.service;

import com.java1234.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 博主Service接口
 *
 * @author Administrator
 */
public interface CommentService {

    /**
     * 获取Comment集合
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
