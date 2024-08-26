package edu.ifsp.web.carrinho;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ifsp.modelo.Carrinho;

@WebServlet("/carrinho/remover")
public class CarrinhoRemoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/*
	 * FIXME
	 * Requisições HTTP GET devem ser idempotentes. Não poderia usá-las para adicionar ou remover itens do carrinho.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Carrinho carrinho = (Carrinho)session.getAttribute("carrinho");
			int pos = Integer.parseInt(request.getParameter("idx"));
			carrinho.remover(pos);
			response.sendRedirect(request.getContextPath() + "/carrinho/visualizar");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
