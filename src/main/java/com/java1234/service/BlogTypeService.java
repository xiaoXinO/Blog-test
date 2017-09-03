package com.java1234.service;

import java.util.List;
import java.util.Map;

import com.java1234.entity.BlogType;

/**
 * 博客类型Service接口
 *
 * @author Administrator
 */
public interface BlogTypeService {

    /**
     * 查询所有博客类型，以及对应的博客数量
     *
     * @return
     */
    public List<BlogType> countList();

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
