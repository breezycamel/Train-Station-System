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

import trainstation.help.QuestionHelp;
import trainstation.model.Question;

import javax.servlet.annotation.WebServlet;
@WebServlet("/rep")

public class RepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public void init() {
    }
	
    public RepServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Question> questions;
		try {
		questions = QuestionHelp.getQuestions();
		request.setAttribute("questions", questions);
	
		}
		catch (Exception e) {
			request.setAttribute("error", e);
			e.printStackTrace();
			
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Rep/question.jsp");
		dispatcher.forward(request, response);
		return;
	}
}