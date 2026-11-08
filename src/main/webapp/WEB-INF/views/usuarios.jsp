<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Utilizadores (UserDTO) — Plataforma SoS</title>
    <style>
        :root {
            --cor-fundo: #f6f5f2;
            --cor-superficie: #ffffff;
            --cor-texto: #1d1f21;
            --cor-marca: #0f4c5c;
            --cor-borda: #dcdad5;
            --cor-ok: #2e7d32;
            --cor-inativo: #b3261e;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
            background-color: var(--cor-fundo);
            color: var(--cor-texto);
            margin: 0;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
        }
        .container {
            background: var(--cor-superficie);
            border: 1px solid var(--cor-borda);
            border-radius: 4px;
            max-width: 860px;
            width: 100%;
            padding: 32px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        h1 {
            color: var(--cor-marca);
            font-size: 24px;
            margin-top: 0;
            margin-bottom: 8px;
        }
        .subtitulo {
            color: #555;
            margin-bottom: 24px;
            font-size: 14px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 16px;
            font-size: 14px;
        }
        th, td {
            text-align: left;
            padding: 10px 12px;
            border-bottom: 1px solid var(--cor-borda);
        }
        th {
            background-color: #f1f0eb;
            color: var(--cor-marca);
            font-weight: 600;
        }
        .badge {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: 600;
        }
        .badge-admin { background: #e0f2fe; color: #0369a1; }
        .badge-autor { background: #fef3c7; color: #92400e; }
        .status-ativo { color: var(--cor-ok); font-weight: 600; }
        .status-inativo { color: var(--cor-inativo); font-weight: 600; }
        
        .formulario {
            background: #faf9f6;
            border: 1px solid var(--cor-borda);
            padding: 20px;
            border-radius: 4px;
            margin-top: 32px;
        }
        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 12px;
            margin-bottom: 12px;
        }
        label {
            font-size: 13px;
            font-weight: 600;
            display: block;
            margin-bottom: 4px;
        }
        input, select {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid var(--cor-borda);
            border-radius: 3px;
            box-sizing: border-box;
            font-size: 14px;
        }
        button {
            background: var(--cor-marca);
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 3px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
        }
        button:hover {
            opacity: 0.9;
        }
        .nav-links {
            margin-bottom: 16px;
        }
        .nav-links a {
            color: var(--cor-marca);
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">← Voltar ao Início</a>
        </div>

        <h1>Demonstração do Objeto UserDTO</h1>
        <div class="subtitulo">
            Exibição do padrão MVC: <strong>UserServlet</strong> (Controller) alimenta a View através de instâncias de <strong>ao.sos.hub.model.UserDTO</strong>.
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Nº Estudante</th>
                    <th>Email</th>
                    <th>Papel</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${listaUtilizadores}">
                    <tr>
                        <td><c:out value="${u.id}"/></td>
                        <td><strong><c:out value="${u.nome}"/></strong></td>
                        <td><c:out value="${u.numeroEstudante}"/></td>
                        <td><c:out value="${u.email}"/></td>
                        <td>
                            <span class="badge ${u.papel == 'ADMIN' ? 'badge-admin' : 'badge-autor'}">
                                <c:out value="${u.papel}"/>
                            </span>
                        </td>
                        <td>
                            <span class="${u.activo ? 'status-ativo' : 'status-inativo'}">
                                <c:out value="${u.activo ? '● Ativo' : '○ Inativo'}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="formulario">
            <h3 style="margin-top: 0; font-size: 16px;">Adicionar Novo Utilizador (Simular DTO)</h3>
            <form action="${pageContext.request.contextPath}/users" method="POST">
                <div class="form-grid">
                    <div>
                        <label for="nome">Nome Completo:</label>
                        <input type="text" id="nome" name="nome" required placeholder="Ex.: Manuel Bento">
                    </div>
                    <div>
                        <label for="numeroEstudante">Nº Estudante:</label>
                        <input type="text" id="numeroEstudante" name="numeroEstudante" placeholder="Ex.: 2024102">
                    </div>
                    <div>
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required placeholder="email@isptec.co.ao">
                    </div>
                    <div>
                        <label for="papel">Papel:</label>
                        <select id="papel" name="papel">
                            <option value="AUTOR">AUTOR (Colega)</option>
                            <option value="ADMIN">ADMIN (Plataforma)</option>
                        </select>
                    </div>
                </div>
                <button type="submit">Adicionar via DTO</button>
            </form>
        </div>
    </div>
</body>
</html>

