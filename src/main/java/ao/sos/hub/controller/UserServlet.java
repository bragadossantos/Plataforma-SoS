package ao.sos.hub.controller;

import ao.sos.hub.model.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsavel por gerir a demonstracao e listagem de utilizadores.
 * Ilustra o padrao MVC enviando instancias de UserDTO para a View JSP.
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/users", "/utilizadores"})
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Lista simulada em memoria para demonstracao da avaliacao
    private final List<UserDTO> utilizadoresDemonstracao = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializa com dados realistas da turma e da plataforma
        utilizadoresDemonstracao.add(new UserDTO(
                1L, "Adriano Tchiloya", "2024001", "adriano.tchiloya@isptec.co.ao", "ADMIN", true
        ));
        utilizadoresDemonstracao.add(new UserDTO(
                2L, "Colega Biblioteca", "2024045", "biblioteca.autor@isptec.co.ao", "AUTOR", true
        ));
        utilizadoresDemonstracao.add(new UserDTO(
                3L, "Colega Farmacia", "2024089", "farmacia.autor@isptec.co.ao", "AUTOR", false
        ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Envia a lista de DTOs como atributo do pedido para a JSP (Camada de Apresentacao)
        req.setAttribute("listaUtilizadores", utilizadoresDemonstracao);
        req.getRequestDispatcher("/WEB-INF/views/usuarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nome = req.getParameter("nome");
        String numero = req.getParameter("numeroEstudante");
        String email = req.getParameter("email");
        String papel = req.getParameter("papel");

        if (nome != null && !nome.isBlank() && email != null && !email.isBlank()) {
            long novoId = utilizadoresDemonstracao.size() + 1L;
            UserDTO novoUser = new UserDTO(novoId, nome.trim(), numero != null ? numero.trim() : "", email.trim(), papel != null ? papel : "AUTOR", true);
            utilizadoresDemonstracao.add(novoUser);
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}

