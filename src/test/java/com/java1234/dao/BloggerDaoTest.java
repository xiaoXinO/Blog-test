package com.java1234.dao;

import com.java1234.entity.Blogger;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by xin on 2017/8/27.
 * ≈‰÷√spring∫Õjunit’˚∫œ
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/applicationContext.xml"})
public class BloggerDaoTest extends TestCase {

    @Resource
    private BloggerDao bloggerDao;

    @Test
    public void testGetByUserName() throws Exception {

    }

    @Test
    public void testFind() throws Exception {
        Blogger blogger = bloggerDao.find();
        System.out.println(blogger.getNickName());
        System.out.println(blogger.toString());
    }

    @Test
    public void testUpdate() throws Exception {

    }
}