package edu.ifsp.web.produto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ifsp.modelo.Produto;
import edu.ifsp.persistencia.ProdutoDAO;
import edu.ifsp.web.Command;
import edu.ifsp.web.templates.Template;

public class SalvarProduto implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Produto produto = new Produto();
		if (!validate(request)) {
			request.setAttribute("produto", produto);
			Template.render("produto/editar", request, response);
			return;
		}
				
		
		if (request.getParameter("id") != null) {
			produto.setId(Integer.parseInt(request.getParameter("id")));
		}
		produto.setDescricao(request.getParameter("descricao"));
		produto.setPreco(Double.parseDouble(request.getParameter("preco")));
		
		ProdutoDAO dao = new ProdutoDAO();
		dao.save(produto);
		response.sendRedirect("recuperar?id=" + produto.getId());		
	}
	
	
	
	
	
	
	
	
	
	

	private boolean validate(HttpServletRequest request) {
		boolean valid = true;
		
		String descricao = request.getParameter("descricao");
		if (descricao == null || descricao.isBlank()) {
			valid = false;
			request.setAttribute("erroDescricao", "Este campo não pode ficar em branco.");
		}
		
		try {
			double preco = Double.parseDouble(request.getParameter("preco"));
			if (preco < 0) {
				valid = false;
				request.setAttribute("erroPreco", "O valor deste campo não pode ser negativo.");
			}
		} catch (NumberFormatException e) {
			valid = false;
			request.setAttribute("erroPreco", "Valor inválido: " + request.getParameter("preco"));
		}
		
				
		return valid;
	}
	
}
