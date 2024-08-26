package edu.ifsp.web.produto;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Produto;
import edu.ifsp.persistencia.ProdutoDAO;
import edu.ifsp.web.Command;
import edu.ifsp.web.Flash;
import edu.ifsp.web.templates.Template;

public class ListarProduto implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> produtos = dao.findAll();
		request.setAttribute("produtos", produtos);
		Flash.move(request, "carrinho");
		Template.render("produto/listar", request, response);		
	}

}
