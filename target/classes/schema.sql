CREATE TABLE IF NOT EXISTS tb_user (
  idUser SERIAL PRIMARY KEY,
  nome VARCHAR(30) NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  situacaoUsuario CHAR(1) CHECK (situacaoUsuario IN ('A', 'I')),
  dataCriacao TIMESTAMP DEFAULT NOW(),
  dataAtualizacao TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tb_ticket (
    idTicket SERIAL PRIMARY KEY,
    numTicket INT NOT NULL,
    dataEntregaTicket TIMESTAMP DEFAULT NOW(),
    atualizacaoEntregaTicket TIMESTAMP DEFAULT NOW(),
    quantidade INT NOT NULL,
    idUser INT,

    CONSTRAINT fk_user FOREIGN KEY (idUser) REFERENCES tb_user(iduser),
    CONSTRAINT chk_numTicket_pos CHECK(numTicket > 0)
);