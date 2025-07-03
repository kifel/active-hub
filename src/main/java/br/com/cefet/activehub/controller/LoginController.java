package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import br.com.cefet.activehub.dao.UsuarioDAO;
import br.com.cefet.activehub.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String msgErro = null;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = null;

        try {
            usuario = usuarioDAO.buscarPorLogin(login);
        } catch (SQLException e) {
            e.printStackTrace(); // Ideal usar log
            msgErro = "Erro ao acessar o banco de dados.";
        }

        if (usuario == null || !BCrypt.checkpw(senha, usuario.getPassword())) {
            msgErro = "Login ou senha inválidos.";
            request.setAttribute("msg", msgErro);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        Cookie cookieFundo = new Cookie("corFundo", usuario.getCorFundo());
        Cookie cookieFonte = new Cookie("corFonte", usuario.getCorFonte());

        // Duração dos cookies: 7 dias (em segundos)
        cookieFundo.setMaxAge(7 * 24 * 60 * 60);
        cookieFonte.setMaxAge(7 * 24 * 60 * 60);

        // Tornar os cookies acessíveis em todo o sistema
        cookieFundo.setPath("/");
        cookieFonte.setPath("/");

        response.addCookie(cookieFundo);
        response.addCookie(cookieFonte);

        // Login válido
        HttpSession session = request.getSession();
        usuario.setPassword(null); // Remove a senha por segurança
        session.setAttribute("usuario", usuario);
        session.setAttribute("tokenId", UUID.randomUUID().toString());

        response.sendRedirect(request.getContextPath() + "/views/menu.jsp");
    }
}
