package edu.ifsp.web.produto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Produto;
import edu.ifsp.persistencia.ProdutoDAO;
import edu.ifsp.web.Command;
import edu.ifsp.web.templates.Template;

public class RecuperarProduto implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		ProdutoDAO dao = new ProdutoDAO();
		Produto p = dao.findById(id);
		request.setAttribute("produto", p);
		Template.render("produto/detalhes", request, response);					
	}

}
