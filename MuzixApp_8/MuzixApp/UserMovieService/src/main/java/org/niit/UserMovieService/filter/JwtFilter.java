package org.niit.UserMovieService.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean{
    // Generic filter bean is a simple javax.servlet.filter implementation . it extends object, implements filter,disposable bean, initializing bean, environment capable and environment aware , bean name aware, the servlet context aware,it  is a handy super class for any type of filter
    // type conversions of config parameters  is automatic with the corresponding setter methods
    // The Generic filter base class has no dependency on spring application context
    // It is the simple base implementation of filter which treats its config parameters as bean properties.
    // This filter leaves actual filtering to sub-classes which have to implement the filter.dofilter(Jarkarta.Servlet.ServletRequest,Jarkarta.Servlet.ServletResponse,Jarkarta.Servlet.filterChain).
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //typecast servletRequest /response to Httpservlet request/response
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authHeader = httpServletRequest.getHeader("Authorization");
        System.out.println("Authheader = "+ authHeader);
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        if(authHeader==null || !authHeader.startsWith("Bearer")) {
            //token is missing or invalid token
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            servletOutputStream.print("missing or invalid token");
            servletOutputStream.close();
        }
        else {
            //token is valid
            String jwttoken = authHeader.substring(7);
            System.out.println("jwttoken "+jwttoken);
            String username = Jwts.parser().setSigningKey("securityKey").parseClaimsJws(jwttoken).getBody().getSubject();
            httpServletRequest.setAttribute("firstName", username);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
