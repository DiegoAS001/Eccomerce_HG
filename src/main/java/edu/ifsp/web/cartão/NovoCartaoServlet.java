package edu.ifsp.web.cartão;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Cartao;
import edu.ifsp.persistencia.CartaoDAO;
import edu.ifsp.persistencia.PersistenceException;
import edu.ifsp.web.templates.Template;


@WebServlet("/cartao/cadastro")
public class NovoCartaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Template.render("cartao/cadastroCartao", request, response);		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			CartaoDAO dao = new CartaoDAO();
			Cartao cartao = new Cartao();
			
			cartao.setNome(request.getParameter("nome"));
			cartao.setNumero(request.getParameter("numero"));
			cartao.setCvv(Integer.parseInt(request.getParameter("cvv")));
			cartao.setData_vencimento(request.getParameter("data_vencimento"));
			
			
			dao.save(cartao);
				
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}
		
		
		response.sendRedirect(request.getContextPath() + "/carrinho/visualizar");
	}
}


