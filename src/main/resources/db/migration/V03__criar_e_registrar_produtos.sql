CREATE TABLE produto(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
    codigo_produto VARCHAR (100),
    valor DECIMAL (10,2) NOT NULL,
    promocao boolean NOT NULL,
    valor_promo DECIMAL (10,2),
    categoria VARCHAR (100) NOT NULL,
    imagem VARCHAR (100) NOT NULL,
    quantidade BIGINT (100) NOT NULL,
    id_fornecedor BIGINT(20) NOT NULL,
 	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id)
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('IPhone 12', '1009', '7999.00', '1', '5999.99', 'Celular', 'iphone.jpeg', '2', '1');
INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('WindowsPhone', '0807', '2500.99', '0', null, 'Celular', 'wphone.jpeg', '2', '2');
INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('XiaomiPro', '0605', '2999.50', '1', '1099.99', 'Celular', 'xphone.jepg', '1', '3');
INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('Sansung S20', '0403', '10200.99', '0', null, 'Celular', 'sphone.jpeg', '5', '4');
INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('LG Max', '0201', '1000.00', '1', '500.99', 'Celular', 'lgphone.jpeg', '6', '5');