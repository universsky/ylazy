package com.light.sword.ylazy.filter;

import com.light.sword.ylazy.config.DomainConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jack on 2017/2/18.
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {

    @Autowired
    DomainConfig domainConfig;

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("执行过滤操作");
        HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        setDomain(req);

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
    }

    /**
     * 把domainName，port放到session中
     * @param request
     */
    private void setDomain(HttpServletRequest request) {
        String domainName = domainConfig.getDomainName();
        String port = domainConfig.getPort();

        HttpSession session = request.getSession();
        session.setAttribute("domainName", domainName);
        session.setAttribute("port", port);
    }

}
