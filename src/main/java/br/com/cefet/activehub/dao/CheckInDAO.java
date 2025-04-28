package br.com.cefet.activehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.cefet.activehub.enums.CheckEnum;
import br.com.cefet.activehub.model.CheckIn;

public class CheckInDAO extends GenericDAO<CheckIn> {

    @Override
    public CheckIn insert(CheckIn checkIn) throws SQLException {
        String sql = "INSERT INTO checkin (data, tipo, cliente_id) VALUES (?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, checkIn.getData());
            stmt.setString(2, checkIn.getTipo().name()); // Armazenando o tipo de CheckEnum
            stmt.setInt(3, checkIn.getCliente().getId()); // Cliente já deve existir

            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                checkIn.setId(rs.getInt(1));
            }
        }
        return checkIn;
    }

    @Override
    public CheckIn update(CheckIn checkIn) throws SQLException {
        String sql = "UPDATE checkin SET data = ?, tipo = ?, cliente_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, checkIn.getData());
            stmt.setString(2, checkIn.getTipo().name());
            stmt.setInt(3, checkIn.getCliente().getId());
            stmt.setInt(4, checkIn.getId());

            stmt.executeUpdate();
        }
        return checkIn;
    }

    @Override
    public boolean delete(CheckIn checkIn) throws SQLException {
        String sql = "DELETE FROM checkin WHERE id = ?";
        int rowsAffected = 0;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, checkIn.getId());
            stmt.executeUpdate();
            rowsAffected = stmt.executeUpdate();
        }
        return rowsAffected > 0;
    }

    @Override
    public CheckIn findById(int id) throws SQLException {
        String sql = "SELECT * FROM checkin WHERE id = ?";
        CheckIn checkIn = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                checkIn = CheckIn.builder()
                        .id(rs.getInt("id"))
                        .data(rs.getTimestamp("data"))
                        .tipo(CheckEnum.valueOf(rs.getString("tipo")))
                        .build();
            }
        }
        return checkIn;
    }

    @Override
    public List<CheckIn> findAll() throws SQLException {
        String sql = "SELECT * FROM checkin";
        List<CheckIn> checkIns = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CheckIn checkIn = CheckIn.builder()
                        .id(rs.getInt("id"))
                        .data(rs.getTimestamp("data"))
                        .tipo(CheckEnum.valueOf(rs.getString("tipo")))
                        .build();
                checkIns.add(checkIn);
            }
        }
        return checkIns;
    }
}
