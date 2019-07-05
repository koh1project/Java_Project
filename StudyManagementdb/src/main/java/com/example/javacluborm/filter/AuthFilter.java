package com.example.javacluborm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

	public AuthFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	   throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		String uri = req.getRequestURI(); // getRequestURI()はStringを返すメソッド
		//		boolean isStaticCss = uri.startsWith("/javacluborm/css/");
		//		boolean isStaticJs = uri.startsWith("/javacluborm/js/");
		//		boolean endLogin = uri.endsWith("/login");
		boolean isNotStaticCss = false;
		boolean isNotStaticJs = false;
		boolean isNotStaticPhoto = false;
		boolean isNotEndLogin = false;

		if (!uri.startsWith("/javacluborm/css/")) {
			isNotStaticCss = true;
		}
		if (!uri.startsWith("/javacluborm/js/")) {
			isNotStaticJs = true;
		}

		if (!uri.startsWith("/javacluborm/photo/")) {
			isNotStaticPhoto = true;
		}

		if (!uri.endsWith("/login")) {
			isNotEndLogin = true;
		}

		// System.out.println(isStaticCss);
		// System.out.println(isStaticCss);
		// System.out.println(endLogin);
		// System.out.println(uri);

		//		System.out.println("CSSでない" + isNotStaticCss);
		//		System.out.println("JSでない" + isNotStaticJs);
		//		System.out.println("/loginでない" + isNotEndLogin);
		if (isNotEndLogin) {
			if (isNotStaticJs) {
				if (isNotStaticCss) {
					if (isNotStaticPhoto) {
						if (session.getAttribute("loginNameId") == null) {
							System.out.println("リダイレクト : " + uri);
							res.sendRedirect("login");
							return;
						}
					}
				}
			}
		}
		//		System.out.println("NG要素 : " + uri);

		// if (!endLogin) {
		// if (!isStaticCss || !isStaticJs) {
		// if (session.getAttribute("loginNameId") == null) {
		// res.sendRedirect("login");
		// return;
		// }
		// }
		// }

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
