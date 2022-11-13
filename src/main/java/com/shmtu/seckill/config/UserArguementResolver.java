package com.shmtu.seckill.config;

import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.service.IUserService;
import com.shmtu.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArguementResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService iUserService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> classz = parameter.getParameterType();

        return classz==User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse((HttpServletResponse.class));
        String cookie = CookieUtil.getCookieValue(request,"userTicket");
        if(StringUtils.isEmpty(cookie)){
            return null;
        }
        //User user = (User)session.getAttribute(cookie);
        return iUserService.getUserByCookie(cookie, request, response);
    }
}
