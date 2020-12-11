CREATE TABLE cliente(
 	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 	nome VARCHAR(100) NOT NULL,
 	email VARCHAR (100) NOT NULL,
 	senha VARCHAR (100) NOT NULL,
 	documento VARCHAR (60) NOT NULL,
 	data_cadastro DATE
 	
 	
 	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Kauan Drey', 'kauan.drey@gft.com', 'kdrey', '436.353.12', '2020-12-01');
INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Kaue Drey', 'kaue.drey@gft.com', 'kdrey', '436.353.123', '2020-12-02');
INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Andrea Drey', 'andrea.drey@gft.com', 'adrey', '436.353.12345', '2020-12-03');
INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Marcelo Drey', 'marcelo.drey@gft.com', 'mdrey', '436.353.12678', '2020-12-04');
INSERT INTO cliente (nome, email, senha, documento, data_cadastro) values ('Monique Drey', 'monique.drey@gft.com', 'mdrey', '436.353.12910', '2020-12-05');