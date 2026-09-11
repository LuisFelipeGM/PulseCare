INSERT INTO usuario (id, nome, email, senha, tipo_usuario_id) VALUES
    (9001, 'Medico Teste', 'medico.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 1),
    (9002, 'Enfermeiro Teste', 'enfermeiro.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 2),
    (9003, 'Paciente Teste', 'paciente.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 3),
    (9004, 'Paciente Update Teste', 'paciente.update.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 3),
    (9005, 'Paciente Delete Teste', 'paciente.delete.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 3),
    (9006, 'Paciente Senha Teste', 'paciente.senha.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 3),
    (9007, 'Paciente Senha Erro Teste', 'paciente.senha.erro.seed@pulsecare.com', '$2a$10$FKfhRhHJ4EjcdxVzBN7Fo.NBgbrxbB8HRbKlK1N9KIMm78I5x1lXO', 3);


INSERT INTO consulta (id, paciente_id, medico_id, data_hora, status) VALUES
    (9001, 9003, 9001, '2027-06-15 10:00:00', 'AGENDADA'),
    (9002, 9003, 9001, '2027-06-20 14:30:00', 'AGENDADA');
