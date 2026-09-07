CREATE TABLE historico_consulta (
    id BIGSERIAL PRIMARY KEY,
    consulta_id BIGINT NOT NULL UNIQUE,
    paciente_id BIGINT NOT NULL,
    paciente_nome VARCHAR(255),
    medico_id BIGINT NOT NULL,
    medico_nome VARCHAR(255),
    data_hora TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP
);

CREATE INDEX idx_historico_consulta_paciente_id ON historico_consulta (paciente_id);
CREATE INDEX idx_historico_consulta_paciente_data_hora ON historico_consulta (paciente_id, data_hora);
