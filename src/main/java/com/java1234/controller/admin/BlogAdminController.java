package com.java1234.controller.admin;

import com.java1234.entity.Blog;
import com.java1234.entity.PageBean;
import com.java1234.lucene.BlogIndex;
import com.java1234.service.BlogService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;
import com.sun.corba.se.spi.ior.ObjectKey;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员博客Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Resource
    private BlogService blogService;

    private BlogIndex blogIndex = new BlogIndex();

    /**
     * 保存blog信息
     *
     * @param blog
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Blog blog, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (blog.getId() == null) {
            resultTotal = blogService.add(blog);
            blogIndex.addIndex(blog);
        } else {
            resultTotal = blogService.update(blog);
            blogIndex.updateIndex(blog);
        }
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 用于后台搜索博客
     *
     * @param s_blog
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public String list(Blog s_blog, @RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        List<Blog> blogList = blogService.list(map);
        Long total = blogService.getTotal(map);

        JSONObject result = new JSONObject();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-HH-MM"));

        JSONArray jsonArray = JSONArray.fromObject(blogList, jsonConfig);
        result.put("total", total);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 管理员删除博客
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("delete")
    public String delete(@RequestParam(value = "ids", required = true) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        int resultTotal = 0;
        JSONObject result = new JSONObject();
        if (ids.length() > 0) {
            for (int i = 0; i < idsStr.length; i++) {
                resultTotal = blogService.delete(Integer.parseInt(idsStr[i]));
                blogIndex.deleteIndex(idsStr[i]);
            }
        }
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 根据blogId查询Blog对象
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public String findById(@RequestParam(value = "id", required = true) Integer id,
                           HttpServletResponse response) throws Exception {
        Blog blog = blogService.findById(id);
        JSONObject result = new JSONObject().fromObject(blog);
        ResponseUtil.write(response, result);
        return null;
    }
}












