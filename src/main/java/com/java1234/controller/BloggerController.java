package com.java1234.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java1234.entity.Blogger;
import com.java1234.service.BloggerService;
import com.java1234.util.CryptographyUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * ����Controller��
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Resource
    private BloggerService bloggerService;

    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "xin"));
        try {
            subject.login(token); // ��¼��֤
            return "redirect:/admin/main.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "�û��������������");
            return "login";
        }
    }

    /**
     * ���ڲ�����Ϣ
     *
     * @return
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutMe() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "/foreground/blogger/info.jsp");
        mav.addObject("pageTitle", "���ڲ���-��Դ����ϵͳ");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * downlond
     */
    @RequestMapping("/download")
    public ModelAndView download() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "/foreground/blogger/download.jsp");
        mav.addObject("pageTitle", "Դ������-��Դ����ϵͳ");
        mav.setViewName("mainTemp");
        return mav;
    }
}
