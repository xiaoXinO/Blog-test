package com.java1234.controller.admin;

import com.java1234.entity.BlogType;
import com.java1234.entity.Comment;
import com.java1234.entity.PageBean;
import com.java1234.service.BlogService;
import com.java1234.service.BlogTypeService;
import com.java1234.service.CommentService;
import com.java1234.util.ResponseUtil;
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
 * 管理员博客评论Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Resource
    private CommentService commentService;

    /**
     * 博客评论list.do
     *
     * @param state
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "state", required = false) String state, @RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        map.put("state", state);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Comment> commentList = commentService.list(map);
        Long total = commentService.getTotal(map);

        JSONObject result = new JSONObject();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-HH-MM"));

        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("total", total);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 审核评论
     * @param state
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/review")
    public String review(@RequestParam(value = "state", required = false) Integer state,
                         @RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        int resultTotal = 0;
        JSONObject result = new JSONObject();
        if (ids.length() > 0) {
            for (int i = 0; i < idsStr.length; i++) {
                Comment comment = new Comment();
                comment.setId(Integer.parseInt(idsStr[i]));
                comment.setState(state);
                resultTotal = commentService.update(comment);
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
     * 删除评论
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String review(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        int resultTotal = 0;
        JSONObject result = new JSONObject();
        if (ids.length() > 0) {
            for (int i = 0; i < idsStr.length; i++) {
                resultTotal = commentService.delete(Integer.parseInt(idsStr[i]));
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












