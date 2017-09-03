package com.java1234.controller;

import com.java1234.entity.Blog;
import com.java1234.entity.Comment;
import com.java1234.lucene.BlogIndex;
import com.java1234.service.BlogService;
import com.java1234.service.CommentService;
import com.java1234.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;
    private ModelAndView mainPage;

    /**
     * 请求博客详细信息
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Blog blog = blogService.findById(id);
        mav.addObject("blog", blog);
        //获取关键字
        String keyWords = blog.getKeyWord();
        if (StringUtil.isNotEmpty(keyWords)) {
            String[] keyWord = keyWords.split(" ");
            mav.addObject("keyWords", StringUtil.filterWhite(Arrays.asList(keyWord)));
        } else {
            mav.addObject("keyWords", null);
        }
        //获取上一篇和下一篇
        mav.addObject("pageCode", this.getUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id),
                request.getServletContext().getContextPath()));
        //修改clickHit数量
        blog.setClickHit(blog.getClickHit() + 1);
        blogService.update(blog);
        //获取评论
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("blogId", blog.getId());
        map.put("state", 1);
        List<Comment> commentList = commentService.list(map);
        mav.addObject("commentList", commentList);

        mav.addObject("mainPage", "/foreground/blog/view.jsp");
        mav.addObject("divNum", 1);
        mav.addObject("pageTitle", blog.getTitle() + "开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 获取上一个博客和下一个博客
     *
     * @param lastBlog
     * @param nextBlog
     * @param projectContext
     * @return
     */
    private String getUpAndDownPageCode(Blog lastBlog, Blog nextBlog, String projectContext) {
        StringBuffer pageCode = new StringBuffer();
        if (lastBlog == null || lastBlog.getId() == null) {
            pageCode.append("<p>上一篇: 没有了</p>");
        } else {
            pageCode.append("<p>上一篇:<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + ".html'>"
                    + lastBlog.getTitle() + "</a></p>");
        }
        if (nextBlog == null || nextBlog.getId() == null) {
            pageCode.append("<p>下一篇: 没有了</");
        } else {
            pageCode.append("<p>下一篇:<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + ".html'>"
                    + nextBlog.getTitle() + "</a></p>");
        }

        return pageCode.toString();
    }

    /**
     * 用于搜索博客
     *
     * @param q
     * @return
     * @throws Exception
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q,
                               @RequestParam(value = "page", required = false) String page,
                               HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        BlogIndex index = new BlogIndex();
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        List<Blog> blogList = index.searchBlog(q);

        int pageSize = 3;
        int end = blogList.size() >= (Integer.parseInt(page) * pageSize) ? Integer.parseInt(page) * pageSize : blogList.size();

        mav.addObject("pageCode", getUpAndDownPageCode((Integer.parseInt(page)), blogList.size(), q, pageSize, request.getServletContext().getContextPath()));
        mav.addObject("q", q);
        mav.addObject("blogList", blogList.subList((Integer.parseInt(page) - 1) * pageSize, end));
        mav.addObject("resultTotal", blogList.size());
        mav.addObject("pageTitle", "搜索关键字" + q + "的结果页面:");
        mav.addObject("mainPage", "/foreground/blog/result.jsp");
        mav.setViewName("mainTemp");
        return mav;
    }

    private String getUpAndDownPageCode(Integer page, Integer totalNum, String q, Integer pageSize, String projectContext) {
        StringBuffer result = new StringBuffer();
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        if (totalPage == 0) {
            return "";
        } else {
            result.append("<nav>");
            result.append("<ul class='pager'>");
            if (page <= 1) {
                result.append("<li class='disabled'><a href='#'>上一页</a></li>");
            } else {
                result.append("<li><a href='" + projectContext + "/blog/search.html?page=" + (page - 1) + "&q=" + q + "'>上一个</a></li>");
            }
            if (page >= totalPage) {
                result.append("<li class='disabled'><a href='#'>下一页</a></li>");
            } else {
                result.append("<li><a href='" + projectContext + "/blog/search.html?page=" + (page + 1) + "&q=" + q + "'>下一页</a></li>");
            }
            result.append("</ul>");
            result.append("</nav>");
        }
        return result.toString();
    }
}
