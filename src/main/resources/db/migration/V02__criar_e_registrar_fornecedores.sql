CREATE TABLE fornecedor(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
    cnpj varchar (100) NOT NULL
 	
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO fornecedor (nome, cnpj) values ('Apple', '123');
INSERT INTO fornecedor (nome, cnpj) values ('Microsoft', '456');
INSERT INTO fornecedor (nome, cnpj) values ('Xiaomi', '789');
INSERT INTO fornecedor (nome, cnpj) values ('Sansung', '910');
INSERT INTO fornecedor (nome, cnpj) values ('LG', '1112');