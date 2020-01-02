package com.start.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Enumeration;

public class FeignRequest implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    template.header(name, values);

                }
            }

            // 转发参数
            Enumeration<String> bodyNames = request.getParameterNames();
            if (bodyNames != null) {
                while (bodyNames.hasMoreElements()) {
                    String name = bodyNames.nextElement();
                    template.query(name, request.getParameter(name));
                }
            }
//            try {
//                String s = WxUtils.inputStream2String(request.getInputStream(), request.getCharacterEncoding());
//                if (s.length() > 0)
//                    template.body(s.getBytes(request.getCharacterEncoding()), Charset.forName(request.getCharacterEncoding()));
//            } catch (Exception e) {}
        }
    }

}
