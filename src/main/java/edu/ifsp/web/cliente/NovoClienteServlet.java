package edu.ifsp.web.cliente;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Cliente;
import edu.ifsp.persistencia.ClienteDAO;
import edu.ifsp.persistencia.PersistenceException;
import edu.ifsp.web.Flash;
import edu.ifsp.web.templates.Template;

@WebServlet("/novo-cliente")
public class NovoClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Template.render("cliente/novo", request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ClienteDAO dao = new ClienteDAO();
			Cliente cliente = new Cliente();
			
			cliente.getUsuario().setUsername(request.getParameter("username"));
			cliente.getUsuario().setPassword(request.getParameter("password"));
			cliente.setNome(request.getParameter("nome"));
			cliente.setEmail(request.getParameter("email"));
			cliente.setLogradouro(request.getParameter("logradouro"));
			cliente.setBairro(request.getParameter("bairro"));
			cliente.setCidade(request.getParameter("cidade"));
			cliente.setEstado(request.getParameter("estado"));
			cliente.setCep(request.getParameter("cep"));
			
			dao.save(cliente);			
			
		} catch (PersistenceException pe) {
			Flash.setAttribute(request, "login", "msg", pe.getLocalizedMessage());
			Logger.getGlobal().log(Level.SEVERE, pe.getMessage(), pe);
		}
		
		
		response.sendRedirect(request.getContextPath() + "/login");
	}

}
