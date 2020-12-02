CREATE TABLE fornecedor(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
    cnpj varchar (100) NOT NULL
 	
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO fornecedor (nome, cnpj) values ('Kauan Drey', '1115454544');
