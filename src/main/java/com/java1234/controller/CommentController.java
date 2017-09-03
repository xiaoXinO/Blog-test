package com.java1234.controller;

import com.java1234.entity.Blog;
import com.java1234.entity.Comment;
import com.java1234.service.BlogService;
import com.java1234.service.CommentService;
import com.java1234.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 评论Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;

    @RequestMapping("/save")
    public String save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request, HttpSession session,
                       HttpServletResponse response) throws Exception {
        //比较imageCode是否正确
        String sRand = (String) session.getAttribute("sRand");
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (!imageCode.equals(sRand)) {
            result.put("success", false);
            result.put("errorInfo", "验证码错误！");
        } else {
            String userIp = request.getRemoteAddr(); //获取ip
            comment.setUserIp(userIp);
            if (comment.getId() == null) {
                resultTotal = commentService.add(comment);
                //修改Blog的回复次数
                Blog blog = blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit() + 1);
                blogService.update(blog);
            } else {
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

}
