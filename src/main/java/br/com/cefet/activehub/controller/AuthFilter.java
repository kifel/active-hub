package br.com.cefet.activehub.controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] URLS_LIVRES = {
        "/index.jsp", "/login", "/registrar.jsp", "/usuario"
    };

    private boolean isUrlLivre(String url) {
        for (String livre : URLS_LIVRES) {
            if (url.endsWith(livre)) return true;
        }
        return url.matches(".*\\.(css|js|png|jpg|jpeg|gif|woff|woff2|ttf)$");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURI().replace(req.getContextPath(), "");
        HttpSession session = req.getSession(false);
        boolean logado = session != null && session.getAttribute("tokenId") != null;

        if (logado || isUrlLivre(url)) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
