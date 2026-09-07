CREATE TABLE tipo_usuario (
    id INTEGER NOT NULL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_tipo_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario (id)
);

CREATE TABLE consulta (
    id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES usuario (id),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES usuario (id)
);

INSERT INTO tipo_usuario (id, descricao) VALUES
    (1, 'MEDICO'),
    (2, 'ENFERMEIRO'),
    (3, 'PACIENTE');
