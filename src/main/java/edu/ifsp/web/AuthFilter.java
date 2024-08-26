package edu.ifsp.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
					, urlPatterns = { "/*" })
public class AuthFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	
	private List<String> whitelist = null;
	
	
	public void destroy() {	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		
		final String uri = httpRequest.getRequestURI();
		
		Logger.getGlobal().fine(uri);
		
		if (inWhiteList(uri) || httpRequest.getSession().getAttribute("usuario") != null) {
			chain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		Set<String> noAuthDestinations = Set.of("css", "js", "login", "novo-cliente");
		
		final String contextPath = fConfig.getServletContext().getContextPath();				
		whitelist = new ArrayList<>();
		for (String destination : noAuthDestinations) {
			String path = contextPath + "/" + destination;
			whitelist.add(path);
		}
	}
	
	private boolean inWhiteList(String path) {
		for (String p : whitelist) {
			if (path.startsWith(p)) {
				return true;
			}
		}
		return false;
	}

}
