package com.java1234.service.impl;

import com.java1234.dao.CommentDao;
import com.java1234.entity.Comment;

import com.java1234.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 评论Service实现类
 *
 * @author Administrator
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentDao commentDao;


    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public Integer update(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public Integer delete(Integer id) {
        return commentDao.delete(id);
    }
}
