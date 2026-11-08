<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plataforma SoS — Sistema de Sistemas</title>
    <style>
        :root {
            --cor-fundo: #f6f5f2;
            --cor-superficie: #ffffff;
            --cor-texto: #1d1f21;
            --cor-marca: #0f4c5c;
            --cor-borda: #dcdad5;
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
            max-width: 680px;
            width: 100%;
            padding: 32px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }
        h1 {
            color: var(--cor-marca);
            font-size: 24px;
            margin-top: 0;
        }
        .tag {
            display: inline-block;
            background: #e9f2f4;
            color: var(--cor-marca);
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 13px;
            font-weight: 600;
        }
        ul {
            padding-left: 20px;
            line-height: 1.6;
        }
        footer {
            margin-top: 24px;
            padding-top: 16px;
            border-top: 1px solid var(--cor-borda);
            font-size: 13px;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <span class="tag">Programação IV · 2026/27</span>
        <h1>Plataforma SoS</h1>
        <p>Ambiente base configurado com sucesso e a correr no Apache Tomcat 10.1.</p>
        
        <h3>Módulos Prontos (Avaliação 1):</h3>
        <ul>
            <li><strong>Servidor de Aplicações:</strong> Apache Tomcat 10.1.61</li>
            <li><strong>Build &amp; Gestão:</strong> Apache Maven 3.10.0</li>
            <li><strong>Tecnologia Web:</strong> Jakarta EE (Servlet 6.0 + JSP 3.1)</li>
            <li><strong>Modelo de Domínio:</strong> <code>UserDTO</code> implementado</li>
        </ul>

        <footer>
            Plataforma de Alojamento e Monitorização de Sistemas de Sistemas (SoS)
        </footer>
    </div>
</body>
</html>

