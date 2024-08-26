package edu.ifsp.web.carrinho;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import edu.ifsp.modelo.Carrinho;
import edu.ifsp.modelo.Usuario;
import edu.ifsp.persistencia.CarrinhoDAO;
import edu.ifsp.persistencia.PersistenceException;

@WebListener
public class CarrinhoListener implements HttpSessionListener {

    public void sessionDestroyed(HttpSessionEvent se)  {
    	HttpSession session = se.getSession();
    	Carrinho carrinho = (Carrinho)session.getAttribute("carrinho");
    	if (carrinho == null) {
        	Logger.getGlobal().info("Sem carrinho para salvar.");
    		return;
    	}
    	
    	Usuario usuario = carrinho.getUsuario();
    	CarrinhoDAO dao = new CarrinhoDAO();
    	try {
    		dao.save(carrinho);
        	Logger.getGlobal().info("Carrinho atualizado: %s (%d itens)".formatted(
        			usuario.getUsername(), carrinho.getCount()));
    	} catch (PersistenceException pe) {
    		Logger.getGlobal().log(Level.SEVERE, pe.getMessage(), pe);
    	}
    	
    }
	
}
