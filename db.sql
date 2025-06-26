-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS activehub;
USE activehub;

-- Tabela Endereco (1:1 com Cliente)
CREATE TABLE endereco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(10),
    uf CHAR(2),
    cidade VARCHAR(100),
    numero VARCHAR(10),
    logradouro VARCHAR(100),
    bairro VARCHAR(100),
    complemento VARCHAR(100)
);

-- Tabela Cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    matricula VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    endereco_id INT UNIQUE,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id) ON DELETE SET NULL
);

-- Tabela Atividade
CREATE TABLE atividade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valor DECIMAL(10,2),
    periodo ENUM('MANHA', 'TARDE', 'NOITE') NOT NULL
);

-- Tabela associativa ClienteAtividade (N:N)
CREATE TABLE cliente_atividade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    atividade_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (atividade_id) REFERENCES atividade(id) ON DELETE CASCADE
);

-- Tabela CheckIn
CREATE TABLE checkin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cliente_id INT NOT NULL,
    tipo ENUM('IN', 'OUT') NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

-- Tabela Usuario
CREATE TABLE usuario (
    id bigint PRIMARY KEY auto_increment,
    nome VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
