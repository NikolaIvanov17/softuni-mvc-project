package org.softuni.learningportal.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://i.pinimg.com/originals/e4/37/3c/e4373ce516461700d46ce816933f9ce3.png";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
