package com.java1234.dao;

import java.util.List;
import java.util.Map;

import com.java1234.entity.BlogType;
import org.omg.CORBA.INTERNAL;

/**
 * 博客类型Dao接口
 *
 * @author Administrator
 */
public interface BlogTypeDao {

    /**
     * 查询所有博客类型，以及对应的博客数量
     *
     * @return
     */
    List<BlogType> countList();

    /**
     * 通过id查找博客类型实体
     *
     * @param id
     * @return
     */
    BlogType findById(Integer id);

    /**
     * 获取BlogType集合
     *
     * @return
     */
    List<BlogType> list(Map<String, Object> map);

    /**
     * 获取总记录数
     *
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 添加博客类别
     *
     * @param blogType
     * @return
     */
    Integer add(BlogType blogType);

    /**
     * 根据id删除博客类别
     *
     * @param blogTypeId
     */
    Integer delete(Integer blogTypeId);

    /**
     * 根据id修改博客类别
     *
     * @param blogType
     * @return
     */
    Integer update(BlogType blogType);
}
