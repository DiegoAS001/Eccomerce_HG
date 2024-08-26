package edu.ifsp.web.usuario;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ifsp.modelo.Carrinho;
import edu.ifsp.modelo.Usuario;
import edu.ifsp.persistencia.CarrinhoDAO;
import edu.ifsp.persistencia.PersistenceException;
import edu.ifsp.persistencia.UsuarioDAO;
import edu.ifsp.web.Flash;
import edu.ifsp.web.templates.Template;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Flash.move(request, "login");
		Template.render("common/login", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Logger logger = Logger.getGlobal();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsuarioDAO usuarioDao = new UsuarioDAO();
		Usuario usuario = null;
		try {
			usuario = usuarioDao.check(username, password);
			if (usuario != null) {
				/* salvando usuário na sessão */
				HttpSession session = request.getSession(true);
				session.setAttribute("usuario", usuario);
				/* Buscando carrinho do usuário */
				CarrinhoDAO carrinhoDao = new CarrinhoDAO();
				Carrinho carrinho = carrinhoDao.retrieveFor(usuario);
				carrinho.setUsuario(usuario);
				session.setAttribute("carrinho", carrinho);
	        	logger.info("Carrinho carregado: %s (%d itens)".formatted(
	        			usuario.getUsername(), carrinho.getCount()));
				response.sendRedirect("home");
			} else {
				Flash.setAttribute(request, "login", "auth_error", "Nome de usuário ou senha inválidos.");
				response.sendRedirect("login");			
			}
		} catch (PersistenceException pe) {			
			logger.log(Level.SEVERE, pe.getMessage(), pe);
			throw new ServletException(pe);
		}
		
	}

}
