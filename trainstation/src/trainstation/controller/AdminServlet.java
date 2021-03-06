package trainstation.controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;

import trainstation.help.UserHelp;
import trainstation.model.User;

import javax.servlet.annotation.WebServlet;
@WebServlet("/admin")

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserHelp userHelp;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public void init() {
        userHelp = new UserHelp();
    }
	
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String role = ((User)session.getAttribute("user")).getUserRole();
		if(role.equals("customer")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(role.equals("rep")) {
			response.sendRedirect(request.getContextPath() + "/rep");
			return;
		}
			 
		ArrayList<User> users;
		try {
		users = userHelp.getAllRep();
		request.setAttribute("users", users);
	
		}
		catch (Exception e) {
			request.setAttribute("error", e);
			e.printStackTrace();
			
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin.jsp");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String role = ((User)session.getAttribute("user")).getUserRole();
		if(role.equals("customer")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(role.equals("rep")) {
			response.sendRedirect(request.getContextPath() + "/rep");
			return;
		}
		
		String edit = request.getParameter("edit");
		if(edit.equals("0")) {
			String userName = request.getParameter("userName");
			try {
	            userHelp.deleteUser(userName);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			
			response.sendRedirect(request.getContextPath()+"/admin");
			return;
		}
		if(edit.equals("2")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/editRep.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		String userName = request.getParameter("userName");
		String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String SSN = request.getParameter("SSN");
        String email = request.getParameter("email");
        String userRole = "rep";
        
        User user = new User(userName, firstName, lastName, password, SSN, email, userRole);
        
        try {
        	if(edit.equals("3")) {
        		userHelp.updateUser(user);
        	}
        	else {
        		userHelp.registerUser(user);
        	}
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath()+"/admin");
		return;
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String role = ((User)session.getAttribute("user")).getUserRole();
		if(role.equals("customer")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		else if(role.equals("rep")) {
			response.sendRedirect(request.getContextPath() + "/rep");
			return;
		}
		
		String userName = request.getParameter("userName");
        System.out.println(userName);
        try {
            userHelp.deleteUser(userName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath()+"/admin");
		return;
	}
}
