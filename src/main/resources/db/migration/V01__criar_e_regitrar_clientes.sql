CREATE TABLE cliente(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
 	email VARCHAR (100) NOT NULL,
 	senha VARCHAR (100) NOT NULL,
 	documento VARCHAR (60) NOT NULL,
 	data_cadastro DATE
 	
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Kauan Drey', 'kauan.drey@gft.com', 'kdrey', '436.353.12', '2020-12-01');
