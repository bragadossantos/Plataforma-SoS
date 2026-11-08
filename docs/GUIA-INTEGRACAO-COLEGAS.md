# Guia de Integração — como preparar o teu sistema para a Plataforma SoS

> Documento para enviar aos colegas. Se o teu sistema cumprir esta lista, entra na plataforma sem
> surpresas. Se tiveres dúvidas, abre uma issue "Pedido de integração" no repositório ou fala comigo.

## Resumo em 30 segundos

1. Entregas **um ficheiro `.war`** (não o código-fonte, não o projecto do NetBeans em zip).
2. Tem de correr em **Tomcat 10.1** → usa `jakarta.servlet`, **não** `javax.servlet`.
3. Compilado para **Java 17 ou 21** (no máximo 21).
4. **Nada de caminhos fixos**: nem `http://localhost:8080/MeuProjeto`, nem `href="/css/estilo.css"`.
5. Se usas base de dados: **MySQL 8**, eu dou-te a BD `sos_<slug>`, o utilizador e a password.
6. Junta o ficheiro `WEB-INF/sos.properties` com os dados do sistema.

---

## 1. Formato de entrega

| Item | Regra |
|---|---|
| Ficheiro | `<slug>.war`, máximo 50 MB |
| Slug | minúsculas, números e hífen, 3–31 caracteres, começa por letra. Ex.: `biblioteca`, `gestao-farmacia` |
| URL final | `http://<servidor>/<slug>/` |
| Página inicial | o sistema tem de abrir em `/<slug>/` (configura o `welcome-file` no `web.xml` ou um `index.jsp`) |
| Base de dados | script `.sql` separado, que cria **só as tabelas e dados iniciais** (sem `CREATE DATABASE`, sem `USE`) |

### Como gerar o `.war`

- **Maven:** `mvn clean package` → `target/<nome>.war`.
- **NetBeans (Ant):** *Clean and Build* → `dist/<nome>.war`.
- **NetBeans (Maven) / IntelliJ / Eclipse:** opção *Build* / *Export → WAR file*.

## 2. Compatibilidade técnica

### 2.1 Jakarta, não javax
O Tomcat 10+ usa os pacotes `jakarta.*`. Se o teu código tem `import javax.servlet.*`:

```diff
- import javax.servlet.http.HttpServlet;
+ import jakarta.servlet.http.HttpServlet;
```

Dependências Maven correctas:

```xml
<dependency>
  <groupId>jakarta.servlet</groupId>
  <artifactId>jakarta.servlet-api</artifactId>
  <version>6.0.0</version>
  <scope>provided</scope>
</dependency>
<!-- só se usares JSTL (<c:forEach>, etc.) -->
<dependency>
  <groupId>jakarta.servlet.jsp.jstl</groupId>
  <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
  <version>3.0.0</version>
</dependency>
<dependency>
  <groupId>org.glassfish.web</groupId>
  <artifactId>jakarta.servlet.jsp.jstl</artifactId>
  <version>3.0.1</version>
</dependency>
```

Nas JSP, o taglib do JSTL 3 é `jakarta.tags.core`:
```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

> **Não consegues migrar a tempo?** Avisa-me. O Tomcat 10 consegue converter automaticamente
> aplicações `javax` colocadas na pasta `webapps-javaee`; uso essa via como último recurso,
> mas pode falhar com algumas bibliotecas.

### 2.2 Versão do Java
No `pom.xml`: `<maven.compiler.release>17</maven.compiler.release>` (ou 21).
No NetBeans: *Properties → Sources → Source/Binary Format* ≤ 21.

### 2.3 Bibliotecas
Todas as bibliotecas que usas (driver MySQL, JSTL, Gson, etc.) têm de ir **dentro** do `.war`,
em `WEB-INF/lib`. **Não** incluas o `servlet-api.jar` (o Tomcat já o tem).

## 3. Caminhos e URLs — o erro nº 1

O teu sistema vai correr em `/<slug>/`, não em `/`. Links absolutos partem tudo.

```jsp
<%-- Errado --%>
<link rel="stylesheet" href="/css/estilo.css">
<a href="http://localhost:8080/MeuProjeto/login">Entrar</a>
response.sendRedirect("/login");

<%-- Certo --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
<a href="${pageContext.request.contextPath}/login">Entrar</a>
response.sendRedirect(request.getContextPath() + "/login");
```

Em JavaScript, se fazes `fetch("/api/...")`, passa o contexto pela página:
```jsp
<script>const BASE = "${pageContext.request.contextPath}";</script>
```

## 4. Base de dados

- Eu crio para ti: BD **`sos_<slug>`**, utilizador **`<slug>`** com permissões só nessa BD, e envio-te a password em privado.
- No teu sistema, a ligação fica:
  ```
  jdbc:mysql://localhost:3306/sos_<slug>?useSSL=false&serverTimezone=Africa/Luanda&allowPublicKeyRetrieval=true
  ```
- Põe URL, utilizador e password num ficheiro `WEB-INF/classes/db.properties` (ou numa classe de configuração única), **não espalhados pelo código**, para eu os poder ajustar se for preciso.
- O script `.sql` deve correr sem erros numa BD vazia.
- Usas outro SGBD (PostgreSQL, SQL Server)? Fala comigo antes.

## 5. Ficheiro `WEB-INF/sos.properties`

Coloca em `src/main/webapp/WEB-INF/sos.properties` (Maven) ou `web/WEB-INF/sos.properties` (NetBeans Ant).

```properties
# Obrigatórios
sos.nome=Gestão de Biblioteca
sos.slug=biblioteca
sos.versao=1.0.0
sos.autores=Ana Silva (2023001); João Pedro (2023045)
sos.descricao=Empréstimos, devoluções e catálogo de livros da biblioteca da faculdade.

# Recomendados
sos.categoria=Educação
sos.demo.utilizador=admin
sos.demo.password=admin123
sos.health=/health
sos.bd=sim
```

Usa codificação **UTF-8**.

## 6. Endpoint de saúde (recomendado)

Permite à plataforma saber se o teu sistema está mesmo a funcionar. Basta isto:

```java
@WebServlet("/health")
public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("OK");
    }
}
```

Se não existir, a plataforma verifica a página inicial (`/<slug>/`) e aceita qualquer resposta 2xx/3xx.

## 7. Boas maneiras dentro de um servidor partilhado

- Não uses `System.exit()`.
- Uploads de ficheiros: guarda em `System.getProperty("java.io.tmpdir")` ou numa pasta que eu te indicar — **não** dentro da pasta do `.war` (perde-se a cada actualização).
- Não cries threads que nunca terminam; se criares, pára-as num `ServletContextListener.contextDestroyed`.
- Fecha ligações JDBC (`try-with-resources`).
- Não escrevas logs gigantes; `System.out` moderado chega.

## 8. Checklist antes de submeter

- [ ] Gerei o `.war` e testei-o num Tomcat 10.1 limpo, com o contexto `/<slug>` (não como ROOT).
- [ ] Zero `import javax.servlet`.
- [ ] Todos os links/redirects usam o *context path*.
- [ ] O script `.sql` corre numa BD vazia.
- [ ] A configuração da BD está num único sítio.
- [ ] `WEB-INF/sos.properties` preenchido.
- [ ] (Opcional) `/health` devolve `OK`.

## 9. Como submeter

1. Pede conta em `http://<servidor>/registo` e espera aprovação.
2. Em *Os meus sistemas → Submeter*, envia o `.war`, o `.sql` e preenche os dados.
3. A plataforma mostra logo o relatório de validação. Corrige o que estiver a vermelho.
4. Quando eu aprovar, o sistema fica em `http://<servidor>/<slug>/` e aparece no portal.
5. Para actualizar, submete nova versão no mesmo sistema; a anterior fica guardada.

