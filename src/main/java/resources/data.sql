INSERT INTO produto (nome, tipoMidia, preco, categoria, plataforma) VALUES
('The Last of Us Part II', 'D', 59.99, 'Ação', 'Console'),
('Minecraft', 'D', 29.99, 'Aventura', 'PC'),
('Red Dead Redemption 2', 'D', 49.99, 'Aventura', 'Console'),
('Cyberpunk 2077', 'D', 39.99, 'RPG', 'PC'),
('Gran Turismo 7', 'D', 69.99, 'Corrida', 'Console'),
('Hades', 'D', 24.99, 'Roguelike', 'PC'),
('God of War', 'D', 39.99, 'Ação', 'Console'),
('Assassin s Creed Valhalla', 'D', 59.99, 'Ação', 'PC'),
('FIFA 23', 'D', 49.99, 'Esporte', 'Console'),
('The Witcher 3: Wild Hunt', 'D', 29.99, 'RPG', 'PC');

  
INSERT INTO usuario (username, password, salt) VALUES ('das', '1db6b248784f7866500bd34571f2a60fbd3b743e', 'c3ec5fcc-4148-4b2a-86fd-b6b4c9fe928c');
INSERT INTO usuario (username, password, salt) VALUES ('luc', '2af95119c8c3a604b457ce7c4b17c9fdf2dcc034', '78ae0789-9ad5-4f0a-bc2d-20447e2b295c');

insert into cliente (usuario, nome, email, logradouro, bairro, cidade, estado, cep) 
values (1, 'Diego Augusto', 'diego@gmail.com', 'Rua Francisco Pera', 'Jardim Tulipa', 'Araraquara', 'SP', '14808802');

insert into cliente (usuario, nome, email, logradouro, bairro, cidade, estado, cep) 
values (2, 'Lucas Hudson', 'lucas@gmail.com', 'Rua Francisco Pera', 'Jardim Tulipa', 'Araraquara', 'SP', '14808802');


insert into carrinho_item (usuario, produto) values (1, 1);
insert into carrinho_item (usuario, produto) values (1, 3);

SELECT u.username, p.descricao FROM carrinho_item c, usuario u, produto p WHERE c.usuario = u.id AND c.produto = p.id;
select * from usuario;
select * from cliente;
select * from produto;