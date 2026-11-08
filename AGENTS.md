# Contexto permanente do projecto — Plataforma SoS

> Este ficheiro é lido automaticamente pelo agente em todas as conversas deste repositório.
> Mantém-no curto e actualizado. O detalhe vive em `docs/`.

## 1. O que é o projecto

Projecto semestral da cadeira **Programação IV**. Tema: **Sistema de Sistemas (SoS)**.

A **Plataforma SoS** é uma aplicação web Java (Servlets + JSP) que corre num Apache Tomcat e
**alberga, implanta, lista e monitoriza os sistemas web dos outros colegas da turma**.
Cada colega entrega o seu sistema em formato `.war`; a plataforma valida o ficheiro,
faz o deploy no Tomcat e disponibiliza-o num portal único (`http://servidor/<slug>`).

O autor e único contribuidor do código é o dono deste repositório. Os colegas **não** contribuem
para este repositório: interagem apenas através da plataforma (upload do `.war`) ou de issues
de "Pedido de integração".

## 2. Stack (decisões fixas até indicação em contrário)

| Item | Escolha | Nota |
|---|---|---|
| Linguagem | Java 21 (LTS) — compilar com `--release 17` | máquina local tem JDK 22; servidor usa 21 |
| Web | Jakarta Servlet 6.0 + JSP 3.1 + JSTL 3.0 | **`jakarta.*`, nunca `javax.*`** |
| Servidor | Apache Tomcat 10.1.x | a plataforma ocupa o contexto `ROOT` (`/`) |
| Build | Maven (`packaging war`) | `mvn clean package` → `target/ROOT.war` |
| Base de dados | MySQL 8 | via `DataSource` JNDI (`java:comp/env/jdbc/sos`) |
| Frontend | JSP + CSS próprio + JS mínimo (vanilla) | sem frameworks CSS genéricos (ver skill `polimento-interface`) |
| Deploy de sistemas | API de texto do Tomcat Manager (`/manager/text/*`) | utilizador com papel `manager-script` |
| Versionamento | Git + GitHub, fluxo descrito em `docs/GIT-WORKFLOW.md` | |
| Alojamento | VM Ubuntu Server (VMware/VirtualBox) e, opcionalmente, nuvem | ver `docs/INSTALACAO-E-DEPLOY.md` |

## 3. Arquitectura (resumo)

- Padrão **MVC clássico**: `Servlet` (controller) → `Service` (regras) → `DAO` (JDBC) → MySQL; vistas em JSP com JSTL/EL.
- **Proibido scriptlets** (`<% ... %>`) nas JSP novas.
- JSPs ficam em `src/main/webapp/WEB-INF/views/` (não acessíveis directamente).
- Pacote base: `ao.sos.hub` → subpacotes `controller`, `service`, `dao`, `model`, `infra` (Tomcat Manager, validação de WAR, health check), `filter`, `util`.
- Detalhes completos: `docs/REQUISITOS.md`.

## 4. Como trabalhar neste repositório (regras para o agente)

1. **Trabalho por etapas.** O professor pede tarefas específicas com prazos. Cada tarefa:
   - tem um ficheiro `docs/tarefas/TNN-<slug>.md` (modelo em `docs/tarefas/_MODELO.md`);
   - é decomposta em requisitos numerados `TNN-R1, TNN-R2…`;
   - tem issue(s) no GitHub, branch própria e termina com tag de entrega.
   Usa a skill **`nova-tarefa`** sempre que o utilizador colar um enunciado.
2. **Implementar só o que a tarefa pede.** Não adiantar funcionalidades de fases futuras sem perguntar.
   Se algo for ambíguo no enunciado, perguntar antes de assumir.
3. **Se o requisito já estiver implementado**, não reescrever: mostrar onde está (ficheiro + linhas,
   com links clicáveis) e explicar como funciona. Skill **`localizar-codigo`**.
4. **Ambiente/infra** (Tomcat, MySQL, VM, comandos): skill **`ambiente-tomcat`**. Dar sempre comandos
   prontos para PowerShell (Windows local) e/ou bash (Ubuntu na VM), indicando onde se corre cada um.
5. **Sistemas dos colegas**: validar contra o contrato `docs/GUIA-INTEGRACAO-COLEGAS.md`. Skill **`validar-sistema-colega`**.
6. **Git**: nunca trabalhar directamente em `main`. Skill **`fluxo-git`**.
7. **Antes de dar uma tarefa por concluída**: correr as skills **`polimento-codigo`** e, se houver UI,
   **`polimento-interface`**.
8. O autor tem de conseguir **explicar e defender cada linha** na apresentação. Preferir soluções simples
   e legíveis a soluções "espertas". Quando introduzires um conceito novo, explica-o em 2–3 frases.

## 5. Convenções de código

- Identificadores de domínio em **português** (`SistemaColega`, `SubmissaoService`, `listarActivos()`);
  termos técnicos consagrados em inglês ficam em inglês (`DAO`, `Servlet`, `Filter`, `health`).
- Uma classe pública por ficheiro; métodos curtos; sem código morto nem imports não usados.
- `PreparedStatement` sempre (nunca concatenar SQL). `try-with-resources` para JDBC e streams.
- Passwords com hash (BCrypt ou PBKDF2), nunca em texto simples.
- Saída em JSP escapada (`<c:out>` / `fn:escapeXml`). CSRF token nos formulários POST.
- Configuração sensível (passwords do Manager, BD) fora do código: `context.xml` do Tomcat ou variáveis de ambiente. **Nunca** commitar credenciais.
- Mensagens ao utilizador final em português (PT/Angola), tom directo e institucional.

## 6. Língua

Responder sempre em **português** (variante europeia/angolana: "ficheiro", "utilizador", "ecrã").
Commits, issues e documentação também em português.

## 7. Mapa da documentação

| Ficheiro | Para quê |
|---|---|
| `docs/PROMPT.md` | prompts prontos para iniciar sessões e tarefas |
| `docs/REQUISITOS.md` | requisitos funcionais/não funcionais, arquitectura, modelo de dados |
| `docs/GUIA-INTEGRACAO-COLEGAS.md` | contrato que os sistemas dos colegas têm de cumprir |
| `docs/INSTALACAO-E-DEPLOY.md` | instalar JDK/Maven/Tomcat/MySQL, VM, alojamento |
| `docs/GIT-WORKFLOW.md` | branches, commits, tags, issues, releases |
| `docs/ROADMAP.md` | fases e ideias de evolução |
| `docs/tarefas/` | uma ficha por tarefa do professor + matriz de rastreabilidade |

