package com.java1234.controller.admin;

import com.java1234.entity.Blog;
import com.java1234.entity.BlogType;
import com.java1234.entity.PageBean;
import com.java1234.lucene.BlogIndex;
import com.java1234.service.BlogService;
import com.java1234.service.BlogTypeService;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;
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
 * ����Ա�������Controller��
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private BlogService blogService;

    /**
     * ���ں�̨��������
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BlogType> blogTypeList = blogTypeService.list(map);
        Long total = blogTypeService.getTotal(map);

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
        result.put("total", total);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ����Աɾ���������
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids", required = true) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        int resultTotal = 0;
        JSONObject result = new JSONObject();
        if (ids.length() > 0) {
            for (int i = 0; i < idsStr.length; i++) {
                if (blogService.findByTypeId(Integer.parseInt(idsStr[i])) > 0) {
                    result.put("exist", "�ò���������в��ͣ�����ɾ���˲������!");
                } else {
                    resultTotal = blogTypeService.delete(Integer.parseInt(idsStr[i]));
                }
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
     * ���沩�������Ϣ
     *
     * @param blogType
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(BlogType blogType, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        int resultTotal = 0;
        if (blogType.getId() == null) {
            resultTotal = blogTypeService.add(blogType);
        } else {
            resultTotal = blogTypeService.update(blogType);
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












