package com.vimukthimk.reservation_app.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vimukthimk.reservation_app.model.VehicleService;
import com.vimukthimk.reservation_app.dao.VehicleServiceDao;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Servlet implementation class VehicleServiceServlet
 */
@WebServlet("/")
public class VehicleServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private VehicleServiceDao vehicleServiceDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		vehicleServiceDao = new VehicleServiceDao();
	}
	
/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();

		
		try {
			if ("/insert".equals(action)) {
				 insertVehicleService(request, response);
			} else if ("/delete".equals(action)) {
				 deleteVehicleService(request, response);
			} else {
				 String email = request.getParameter("email");
			    listVehicleService(request, response, email);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
		

	//insert vehicle service
	private void insertVehicleService(HttpServletRequest request, HttpServletResponse response)throws SQLException, IOException 
	{
   
		// sanitizing the user inputs for escape any potentially harmful characters
		//neutralizing the user inputs using escapeHtml4() method
		String date = StringEscapeUtils.escapeHtml4(request.getParameter("date").replaceAll("'", "''")); 
		
		String time = StringEscapeUtils.escapeHtml4(request.getParameter("time").replaceAll("'", "''"));
	
		String location = StringEscapeUtils.escapeHtml4(request.getParameter("location").replaceAll("'", "''"));
		
		String vehicle_no = StringEscapeUtils.escapeHtml4(request.getParameter("vehicle_no").replaceAll("'", "''"));
		
		int mileage = Integer.parseInt(request.getParameter("mileage"));
		
		String message = StringEscapeUtils.escapeHtml4(request.getParameter("message").replaceAll("'", "''"));

		String username = StringEscapeUtils.escapeHtml4(request.getParameter("username").replaceAll("'", "''"));
		
		VehicleService newVehicleService = new VehicleService(date, time, location, vehicle_no, mileage, message, username);
		
		vehicleServiceDao.insertVehicleService(newVehicleService);
		
		response.sendRedirect("list");
		
	}
	
	//delete vehicle service
	private void deleteVehicleService(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException 
	{
		int booking_id = Integer.parseInt(request.getParameter("booking_id"));
		try {
		
			vehicleServiceDao.deleteVehicleService(booking_id);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		response.sendRedirect("list");
	}

	
	//default
	private void listVehicleService(HttpServletRequest request, HttpServletResponse response,String email)throws SQLException, IOException, ServletException 
	{ 

		String username = email;
		try {
			List<VehicleService> listVehicleService = vehicleServiceDao.selectAllVehicleServices(username);
			request.setAttribute("listVehicleSerivice", listVehicleService);
			RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}







