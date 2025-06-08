-- init.sql
CREATE EXTENSION IF NOT EXISTS vector;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    data_cadastro DATE NOT NULL
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id SERIAL PRIMARY KEY,
    id_cliente INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    data_pedido DATE NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

-- Inserindo dados de exemplo
INSERT INTO clientes (nome, email, data_cadastro) VALUES
('João Silva', 'joao.silva@email.com', '2020-01-15'),
('Maria Souza', 'maria.souza@email.com', '2019-11-20'),
('Pedro Santos', 'pedro.santos@email.com', '2021-03-01'),
('Ana Oliveira', 'ana.olivera@email.com', '2020-05-10');

INSERT INTO pedidos (id_cliente, valor_total, data_pedido) VALUES
(1, 150.75, '2020-02-01'),
(2, 300.00, '2019-12-01'),
(1, 50.20, '2020-03-10'),
(3, 220.50, '2021-04-05'),
(2, 100.00, '2020-01-20'),
(4, 75.00, '2020-06-15'),
(2, 450.00, '2020-02-25');