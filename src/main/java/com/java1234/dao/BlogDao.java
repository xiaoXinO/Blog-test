package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.Blog;

/**
 * 博客Dao接口
 *
 * @author Administrator
 */
public interface BlogDao {

    /**
     * 根据日期分月分组查询
     *
     * @return
     */
    List<Blog> countList();

    /**
     * 分页查询博客
     *
     * @param map
     * @return
     */
    List<Blog> list(Map<String, Object> map);

    /**
     * 获取总记录数
     *
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 根据Id过去Blog
     */
    Blog findById(Integer id);

    /**
     * 修改Blog信息
     */
    Integer update(Blog blog);

    /**
     * 查询上一个博客
     *
     * @param id
     * @return
     */
    Blog getLastBlog(Integer id);

    /**
     * 查询下一个博客
     *
     * @param id
     */
    Blog getNextBlog(Integer id);

    /**
     * 添加博客
     *
     * @param blog
     * @return
     */
    Integer add(Blog blog);

    /**
     * 删除博客
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 根据typeId查Blog的数量
     *
     * @param id
     * @return
     */
    Integer findByTypeId(Integer typeId);
}
