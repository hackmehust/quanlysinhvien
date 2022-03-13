package JDBC;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch (theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
			case "DELETE":
				deleteStudent(request, response);
			default: 
				listStudents(request, response);
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		
		String theStudentId = request.getParameter("studentId");
		
		studentDBUtil.deleteStudent(theStudentId);
		
		listStudents(request, response);
		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
	
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
				
		Student theStudent = new Student(id, firstName, lastName, email);
				
		studentDBUtil.updateStudent(theStudent); // create method in type StudentDBUtil
				
		listStudents(request, response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		
		String theStudentID = request.getParameter("studentID");
		
		Student theStudent = studentDBUtil.getStudent(theStudentID);
		
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(firstName, lastName, email);
		studentDBUtil.addStudent(theStudent);
		listStudents(request, response);
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		List<Student> students = studentDBUtil.getStudents();
		
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		
		dispatcher.forward(request, response);
	}

}
