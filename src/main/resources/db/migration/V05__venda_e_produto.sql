CREATE TABLE venda_produto(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	id_venda BIGINT (20) NOT NULL,
 	id_produto BIGINT(20) NOT NULL,
 	FOREIGN KEY (id_venda) REFERENCES venda(id),
 	FOREIGN KEY (id_produto) REFERENCES produto(id)
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO venda_produto (id_venda, id_produto) values ('1', '1');