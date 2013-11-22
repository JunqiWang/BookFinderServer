//package com.wilddynamos.bookappserver;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.AsyncContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 异步上下文的转向分发
// * 
// * @author yongboy
// * @date 2011-1-14
// * @version 1.0
// */
//@WebServlet(urlPatterns = { "/asyncDispatch2Async" }, asyncSupported = true)
//public class AsyncContextDispatch2AsyncServlet extends HttpServlet {
//	private static final long serialVersionUID = 46172233331022236L;
//
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		response.setHeader("Cache-Control", "private");
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Connection", "Keep-Alive");
//		response.setHeader("Proxy-Connection", "Keep-Alive");
//		response.setContentType("text/html;charset=UTF-8");
//
//		PrintWriter out = response.getWriter();
//		out.println("Start ...");
//		out.flush();
//
//		if (!request.isAsyncSupported()) {
//			return;
//		}
//
//		request.startAsync(request, response);
//
//		if (request.isAsyncStarted()) {
//			AsyncContext asyncContext = request.getAsyncContext();
//			asyncContext.setTimeout(1L * 60L * 1000L);// 60sec
//
//			new CounterThread(asyncContext).start();
//		} else {
//		}
//	}
//
//	private static class CounterThread extends Thread {
//		private AsyncContext asyncContext;
//
//		public CounterThread(AsyncContext asyncContext) {
//			this.asyncContext = asyncContext;
//		}
//
//		@Override
//		public void run() {
//			int interval = 1000 * 1; // 20sec
//
//			try {
//				Thread.sleep(interval);
//
//				ServletRequest request = asyncContext.getRequest();
//
//				String disUrl = request.getParameter("disUrl");
//
//				// 将当前异步上下文所持有的request, response分发给Servlet容器
//				if ("self".equals(disUrl)) {
//					// 将分发到自身，即当前异步请求地址
//					asyncContext.dispatch();
//					System.out.println("2");
//				} else {
//					// 将分发到指定的路径
//					asyncContext.dispatch(disUrl);
//				}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				System.out.println(e.toString());
//			}
//		}
//	}
//}
