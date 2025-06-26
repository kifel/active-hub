package br.com.cefet.activehub.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import br.com.cefet.activehub.dao.UsuarioDAO;
import br.com.cefet.activehub.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuario")
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extrair as informações do formulário (resitro.jsp) como texto
        String loginTxt = request.getParameter("login");
        String nomeTxt = request.getParameter("nome");
        String senhaTxt = request.getParameter("senha");
        String msg = null;

        // Criptografar a Senha
        String senhaHash = BCrypt.hashpw(senhaTxt, BCrypt.gensalt());

        // Instanciar um Login
        Usuario usuario = Usuario.builder().nome(nomeTxt).username(loginTxt).password(senhaHash).build();

        // Salvando um usuario do BD
        UsuarioDAO uDao = new UsuarioDAO();
        try {
            uDao.insert(usuario);
            msg = "Usuário salvo. Faça o login!";
        } catch (SQLException e) {
            msg = "Erro ao inserir o Usuário. Tente novamente!";
        }

        // Voltar para o menu
        request.setAttribute("msg", msg);
        RequestDispatcher rd = request.getRequestDispatcher("/views/registrar.jsp");
        rd.forward(request, response);
    }
}
