package ecospy.servlet;

import ecospy.server.Data.MySessionFactory;
import ecospy.server.Utility.IntStringWrapper;
import ecospy.server.BusinessLogic.BLExecutor;
import ecospy.server.Utility.RequestType;
import ecospy.server.Utility.ResponseCodeInterpreter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ServletDemo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2730884609154843411L;
	private static MySessionFactory f;
	private static ResponseCodeInterpreter interpreter;
	public ServletDemo(){
		if(f==null) 
			f = new MySessionFactory();
		if(interpreter==null)
			interpreter = new ResponseCodeInterpreter();
	}
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		//        response.setContentType("text/html");
		//        PrintWriter out = response.getWriter();
		//        request.getContentType();
		//        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		//        out.println("<HTML>");
		//        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		//        out.println("  <BODY>");
		//        out.println(request.getHeader("Request-Type"));
		//        out.println("  </BODY>");
		//        out.println("</HTML>");
		//        out.flush();
		//        out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getHeader("Request-Type");
		RequestType requestType = RequestType.valueOf(type.replace("-", ""));
		response.setContentType("text/plain");
		BLExecutor exe = new BLExecutor();
		IntStringWrapper result = exe.execute(requestType, request);
		response.setStatus(result.getCode());
		OutputStream os = response.getOutputStream(); 
		if(result.getImg()!=null)
			os.write(result.getImg());
		else
			os.write(result.getInfo().getBytes("UTF-8"));     
		os.close();  
	}

}