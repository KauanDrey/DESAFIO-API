CREATE TABLE produto(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
    codigo_produto VARCHAR (100),
    valor DECIMAL (65) NOT NULL,
    promocao boolean NOT NULL,
    valor_promo DECIMAL (65) NOT NULL,
    categoria VARCHAR (100) NOT NULL,
    imagem VARCHAR (100) NOT NULL,
    quantidade BIGINT (100) NOT NULL,
    id_fornecedor BIGINT(20) NOT NULL,
 	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id)
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO produto(nome, codigo_produto, valor, promocao, valor_promo, categoria, imagem, quantidade, id_fornecedor) values ('Kauan Drey', '33333', '3600.12', '1', '5600.00', 'TLOU II', 'jpe', '2', '1');