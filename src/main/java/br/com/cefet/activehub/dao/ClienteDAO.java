package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.enums.PeriodoEnum;
import br.com.cefet.activehub.model.Atividade;
import br.com.cefet.activehub.model.Cliente;
import br.com.cefet.activehub.model.ClienteAtividade;
import br.com.cefet.activehub.model.Endereco;

public class ClienteDAO extends GenericDAO<Cliente> {

    @Override
    public Cliente insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, matricula, is_active, endereco_id) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getMatricula());
            stmt.setBoolean(4, cliente.getIsActive());
            stmt.setInt(5, cliente.getEndereco().getId()); // Supondo que o endereço já está registrado

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
        } finally {
            closeConnection(conn, stmt, null);
        }
        return cliente;
    }

    @Override
    public Cliente update(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, matricula = ?, is_active = ?, endereco_id = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getMatricula());
            stmt.setBoolean(4, cliente.getIsActive());
            stmt.setInt(5, cliente.getEndereco().getId());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
        return cliente;
    }

    @Override
    public boolean delete(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsAffected = 0;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            rowsAffected = stmt.executeUpdate();
        } finally {
            closeConnection(conn, stmt, null);
        }
        return rowsAffected > 0;
    }

    @Override
    public Cliente findById(int id) throws SQLException {
        String sql = "SELECT c.id AS cliente_id, c.nome, c.cpf, c.matricula, c.is_active, " +
                "e.id AS endereco_id, e.cep, e.uf, e.cidade, e.numero, e.logradouro, e.bairro, e.complemento, " +
                "a.id AS atividade_id, a.nome AS atividade_nome, a.valor, a.periodo " +
                "FROM cliente c " +
                "JOIN endereco e ON e.id = c.endereco_id " +
                "LEFT JOIN cliente_atividade ca ON ca.cliente_id = c.id " +
                "LEFT JOIN atividade a ON a.id = ca.atividade_id " +
                "WHERE c.id = ?";

        Cliente cliente = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (cliente == null) {
                    // Monta o cliente com o builder
                    cliente = Cliente.builder()
                            .id(rs.getInt("cliente_id"))
                            .nome(rs.getString("nome"))
                            .cpf(rs.getString("cpf"))
                            .matricula(rs.getString("matricula"))
                            .isActive(rs.getBoolean("is_active"))
                            .endereco(Endereco.builder()
                                    .id(rs.getInt("endereco_id"))
                                    .cep(rs.getString("cep"))
                                    .uf(rs.getString("uf"))
                                    .cidade(rs.getString("cidade"))
                                    .numero(rs.getString("numero"))
                                    .logradouro(rs.getString("logradouro"))
                                    .bairro(rs.getString("bairro"))
                                    .complemento(rs.getString("complemento"))
                                    .build()) // Usando o builder para o Endereco
                            .atividades(new ArrayList<>()) // Inicializa a lista de atividades
                            .build();
                }

                // Adiciona as atividades ao cliente
                if (rs.getInt("atividade_id") != 0) {
                    Atividade atividade = Atividade.builder()
                            .id(rs.getInt("atividade_id"))
                            .nome(rs.getString("atividade_nome"))
                            .valor(rs.getBigDecimal("valor"))
                            .periodo(rs.getString("periodo") != null ? PeriodoEnum.valueOf(rs.getString("periodo"))
                                    : null)
                            .build();

                    ClienteAtividade clienteAtividade = ClienteAtividade.builder()
                            .cliente(cliente)
                            .atividade(atividade)
                            .build();

                    cliente.getAtividades().add(clienteAtividade); // Adiciona a atividade ao cliente
                }
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return cliente;
    }

    @Override
    public List<Cliente> findAll() throws SQLException {
        String sql = "SELECT c.id AS cliente_id, c.nome, c.cpf, c.matricula, c.is_active, " +
                "e.id AS endereco_id, e.cep, e.uf, e.cidade, e.numero, e.logradouro, e.bairro, e.complemento, " +
                "a.id AS atividade_id, a.nome AS atividade_nome, a.valor, a.periodo " +
                "FROM cliente c " +
                "JOIN endereco e ON e.id = c.endereco_id " +
                "LEFT JOIN cliente_atividade ca ON ca.cliente_id = c.id " +
                "LEFT JOIN atividade a ON a.id = ca.atividade_id";

        List<Cliente> clientes = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            Cliente cliente = null;

            while (rs.next()) {
                if (cliente == null || cliente.getId() != rs.getInt("cliente_id")) {
                    // Monta o cliente com o builder
                    cliente = Cliente.builder()
                            .id(rs.getInt("cliente_id"))
                            .nome(rs.getString("nome"))
                            .cpf(rs.getString("cpf"))
                            .matricula(rs.getString("matricula"))
                            .isActive(rs.getBoolean("is_active"))
                            .endereco(Endereco.builder()
                                    .id(rs.getInt("endereco_id"))
                                    .cep(rs.getString("cep"))
                                    .uf(rs.getString("uf"))
                                    .cidade(rs.getString("cidade"))
                                    .numero(rs.getString("numero"))
                                    .logradouro(rs.getString("logradouro"))
                                    .bairro(rs.getString("bairro"))
                                    .complemento(rs.getString("complemento"))
                                    .build()) // Usando o builder para o Endereco
                            .atividades(new ArrayList<>()) // Inicializa a lista de atividades
                            .build();
                    clientes.add(cliente); // Adiciona o cliente à lista
                }

                // Adiciona as atividades ao cliente
                if (rs.getInt("atividade_id") != 0) {
                    Atividade atividade = Atividade.builder()
                            .id(rs.getInt("atividade_id"))
                            .nome(rs.getString("atividade_nome"))
                            .valor(rs.getBigDecimal("valor"))
                            .periodo(rs.getString("periodo") != null ? PeriodoEnum.valueOf(rs.getString("periodo"))
                                    : null)
                            .build();

                    ClienteAtividade clienteAtividade = ClienteAtividade.builder()
                            .cliente(cliente)
                            .atividade(atividade)
                            .build();

                    cliente.getAtividades().add(clienteAtividade); // Adiciona a atividade ao cliente
                }
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return clientes;
    }

    public boolean existsByCpf(String cpf) throws SQLException {
        String sql = "SELECT 1 FROM cliente WHERE cpf = ? LIMIT 1";
        boolean exists = false;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return exists;
    }

    public List<Cliente> findByNameContaining(String nome) throws SQLException {
        String sql = "SELECT c.id AS cliente_id, c.nome, c.cpf, c.matricula, c.is_active, " +
                "e.id AS endereco_id, e.cep, e.uf, e.cidade, e.numero, e.logradouro, e.bairro, e.complemento, " +
                "a.id AS atividade_id, a.nome AS atividade_nome, a.valor, a.periodo " +
                "FROM cliente c " +
                "JOIN endereco e ON e.id = c.endereco_id " +
                "LEFT JOIN cliente_atividade ca ON ca.cliente_id = c.id " +
                "LEFT JOIN atividade a ON a.id = ca.atividade_id " +
                "WHERE c.nome LIKE ? " +
                "ORDER BY c.nome";

        List<Cliente> clientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            Cliente cliente = null;

            while (rs.next()) {
                if (cliente == null || cliente.getId() != rs.getInt("cliente_id")) {
                    cliente = Cliente.builder()
                            .id(rs.getInt("cliente_id"))
                            .nome(rs.getString("nome"))
                            .cpf(rs.getString("cpf"))
                            .matricula(rs.getString("matricula"))
                            .isActive(rs.getBoolean("is_active"))
                            .endereco(Endereco.builder()
                                    .id(rs.getInt("endereco_id"))
                                    .cep(rs.getString("cep"))
                                    .uf(rs.getString("uf"))
                                    .cidade(rs.getString("cidade"))
                                    .numero(rs.getString("numero"))
                                    .logradouro(rs.getString("logradouro"))
                                    .bairro(rs.getString("bairro"))
                                    .complemento(rs.getString("complemento"))
                                    .build())
                            .atividades(new ArrayList<>())
                            .build();
                    clientes.add(cliente);
                }

                if (rs.getInt("atividade_id") != 0) {
                    Atividade atividade = Atividade.builder()
                            .id(rs.getInt("atividade_id"))
                            .nome(rs.getString("atividade_nome"))
                            .valor(rs.getBigDecimal("valor"))
                            .periodo(rs.getString("periodo") != null ? PeriodoEnum.valueOf(rs.getString("periodo"))
                                    : null)
                            .build();

                    ClienteAtividade clienteAtividade = ClienteAtividade.builder()
                            .cliente(cliente)
                            .atividade(atividade)
                            .build();

                    cliente.getAtividades().add(clienteAtividade);
                }
            }
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return clientes;
    }

}