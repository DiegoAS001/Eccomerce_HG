/**
 * 
 */

const adicionarUrl = '/lojaweb/carrinho/adicionar';

/*aasync = função asincrona (continua rodando enquanto nao chega a resposta)*/

async function adicionarCarrinho(produtoId){
	console.log('Adicionar ao carrinho:', produtoId);
	const data = new URLSearchParams();
	data.set("produto", produtoId);
	
	const response = await fetch(adicionarUrl, {method: 'POST', body: data});
	if(response.status == 200){
		// mensagem: deu certo
		console.log('Produto adicionado')
	}
}