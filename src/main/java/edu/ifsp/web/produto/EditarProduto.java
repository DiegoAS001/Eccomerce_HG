package edu.ifsp.web.produto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Produto;
import edu.ifsp.persistencia.ProdutoDAO;
import edu.ifsp.web.Command;
import edu.ifsp.web.templates.Template;

public class EditarProduto implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Produto produto = null;

		if (request.getParameter("id") == null) {
			produto = new Produto();			
		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			ProdutoDAO dao = new ProdutoDAO();
			produto = dao.findById(id);
		}
		
		request.setAttribute("produto", produto);
		Template.render("produto/editar", request, response);
	}

}
