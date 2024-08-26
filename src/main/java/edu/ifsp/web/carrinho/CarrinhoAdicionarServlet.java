package edu.ifsp.web.carrinho;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ifsp.modelo.Carrinho;
import edu.ifsp.modelo.Produto;
import edu.ifsp.persistencia.ProdutoDAO;
import edu.ifsp.web.Flash;

@WebServlet("/carrinho/adicionar")
public class CarrinhoAdicionarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			Carrinho carrinho = (Carrinho)session.getAttribute("carrinho");
			
			int produtoId = Integer.parseInt(request.getParameter("produto"));
			ProdutoDAO dao = new ProdutoDAO();
			Produto produto = dao.findById(produtoId);
			carrinho.adicionar(produto);
			//Flash.setAttribute(request, "carrinho", "msg", "Produto adicionado.");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
