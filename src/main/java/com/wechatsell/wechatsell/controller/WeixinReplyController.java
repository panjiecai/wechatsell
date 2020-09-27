package com.wechatsell.wechatsell.controller;

import com.wechatsell.wechatsell.utils.SignUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * 验证 微信 接口配置信息
 */
@RestController
@RequestMapping("/reply")
public class WeixinReplyController {

    @RequestMapping("/get")
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("========WeixinReplyController========= ");
        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            // out.print(name + "=" + value);
            String log = "name =" + name + "     value =" + value;
        }
//        System.out.println("========WeixinReplyControllertets========= ");
        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        PrintWriter out = response.getWriter();

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("校验成功！");
            out.print(echostr);
        }

        out.close();
        out = null;

    }
}
