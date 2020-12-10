CREATE TABLE venda(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR (100) NOT NULL,
 	total_compra DECIMAL (10,2),
 	data_compra DATE NOT NULL,
 	id_fornecedor BIGINT(20) NOT NULL,
 	id_cliente BIGINT(20) NOT NULL,
 	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id),
 	FOREIGN KEY (id_cliente) REFERENCES cliente(id)
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO venda(nome,id_fornecedor, id_cliente, total_compra, data_compra) values ('GERAL', '1', '1', '8000', '2020-11-03');