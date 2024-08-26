# Tarefas pendentes

Neste documento estão listadas algumas melhorias que podem ser implementadas neste projeto. 


## Tarefas

Cada melhoria está descrita a seguir, como uma tarefa.


### Cadastro de clientes

- Confirmação de senha  
No formulário de cadastro de clientes, adicionar um campo *Confirmar senha* e implementar a funcionalidade de comparação deste novo campo com o campo *Senha*. Esta tarefa requer uso de JavaScript.


- Busca por CEP  
Preencher o endereço automaticamente, quando quando o usuário informar o CEP.
Usar o serviço [ViaCEP](https://viacep.com.br/).


- Múltiplos endereços  
Um cliente poderia ter múltiplos endereços. Neste caso, seria necessário criar uma nova classe `Endereco` e a respectiva tabela no banco de dados.