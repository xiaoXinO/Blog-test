package com.java1234.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.entity.Blogger;
import com.java1234.util.CryptographyUtil;
import com.java1234.util.DateUtil;
import com.java1234.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java1234.service.BloggerService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * ����Ա����Controller��
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

    @Resource
    private BloggerService bloggerService;

    /**
     * ��ȡ������Ϣ
     *
     * @return
     */
    @RequestMapping("/find")
    public String find(HttpServletResponse response) throws Exception {
        Blogger blogger = bloggerService.find();
        JSONObject result = JSONObject.fromObject(blogger);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * �޸Ĳ�����Ϣ
     *
     * @param blogger
     * @return
     */
    @RequestMapping("/update")
    public String update(@RequestParam(value = "imageFile") MultipartFile imageFile, Blogger blogger, HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        if (!imageFile.isEmpty()) {
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath + "/static/userImages/" + imageName));
            blogger.setImageName(imageName);
        }
        Integer resultTotal = bloggerService.update(blogger);
        StringBuffer result = new StringBuffer();
        if (resultTotal > 0) {
            result.append("<script type=\"text/javascript\">alert('�޸ĳɹ�')</script>");
        } else {
            result.append("<script type=\"text/javascript\">alert('�޸�ʧ��')</script>");
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * �޸Ĳ�������
     *
     * @param newPassword
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String newPassword, HttpServletResponse response) throws Exception {
        Blogger blogger = new Blogger();
        blogger.setPassword(CryptographyUtil.md5(newPassword, "xin"));
        int resultTotal = bloggerService.update(blogger);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * �˳�ϵͳ
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}