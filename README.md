# 🌐 Plataforma SoS — Sistema de Sistemas (Programação IV)

> **Plataforma web centralizada em Java 21 e Apache Tomcat 10 concebida para albergar, validar, implantar e monitorizar os sistemas web independentes desenvolvidos pelos colegas da turma.**

---

## 📌 Sobre o Projecto

No âmbito da cadeira de **Programação IV**, este projecto implementa na prática o conceito de **Sistema de Sistemas (SoS)**. Em vez de operar como uma aplicação isolada, a plataforma funciona como um ecossistema integrador e hospedeiro:

* 📦 **Recepção e Validação:** Os colegas submetem as suas aplicações em formato `.war`. A plataforma analisa o ficheiro e valida a conformidade técnica (bytecode Java ≤ 21, pacotes `jakarta.*`, integridade estrutural e o manifesto de configuração `sos.properties`).
* 🚀 **Deploy Automatizado:** Através da API do Tomcat Manager (`/manager/text`), a plataforma efetua a implantação, arranque e paragem dinâmica dos sistemas sem necessidade de reiniciar o servidor Tomcat.
* 📊 **Catálogo e Monitorização:** Portal público unificado com a lista de sistemas disponíveis (`http://<servidor>/<slug>/`), detalhe de autores, credenciais de demonstração e *health checks* periódicos para verificar se estão activos.
* 🗄️ **Isolamento de Dados:** Suporte a bases de dados MySQL 8 dedicadas por subsistema (`sos_<slug>`).

---

## 🛠️ Stack Tecnológica

| Componente | Tecnologia | Nota |
|---|---|---|
| **Linguagem** | Java 21 (LTS) | Compilação com `--release 17/21` |
| **Camada Web** | Jakarta Servlet 6.0 + JSP 3.1 + JSTL 3.0 | Padrão MVC (sem scriptlets nas views) |
| **Servidor Web** | Apache Tomcat 10.1.x | Plataforma implantada no contexto raiz (`ROOT`) |
| **Base de Dados** | MySQL 8 | Pool de conexões via `DataSource` JNDI |
| **Build & Gestão** | Apache Maven | Empacotamento em `ROOT.war` |
| **Deploy & Infra** | API Tomcat Manager + VM Ubuntu Server | VMware Workstation / VirtualBox |
| **Versionamento** | Git + GitHub | Conventional Commits e fluxo por branches |

---

## 📖 Documentação do Projecto

A documentação detalhada encontra-se na pasta [`docs/`](docs/):

* 📋 [**Requisitos e Arquitectura**](docs/REQUISITOS.md) — Requisitos funcionais, diagrama de estados, modelo de dados e fundamentação teórica de SoS.
* 🤝 [**Guia de Integração para os Colegas**](docs/GUIA-INTEGRACAO-COLEGAS.md) — Contrato técnico que os `.war` dos colegas devem cumprir para serem aceites pela plataforma.
* ⚙️ [**Instalação, Tomcat e Deploy**](docs/INSTALACAO-E-DEPLOY.md) — Comandos para configurar o ambiente no Windows (PowerShell) e na VM Ubuntu (bash/systemd/Nginx).
* 🔀 [**Fluxo de Versionamento Git**](docs/GIT-WORKFLOW.md) — Estrutura de branches (`main`, `develop`, `feature/*`), formato de commits e criação de tags de entrega.
* 🗺️ [**Roadmap e Ideias**](docs/ROADMAP.md) — Fases de evolução e ideias para valorizar a apresentação do tema.
* 📝 [**Tarefas e Entregas**](docs/tarefas/README.md) — Fichas individuais das tarefas atribuídas pelo professor e matriz de rastreabilidade.

---

## 🚀 Como Executar Localmente

### Pré-requisitos
* Java JDK 21 instalado e configurado no `PATH`
* Apache Maven
* Apache Tomcat 10.1.x
* MySQL Server 8

### Passos Rápidos

1. **Compilar a aplicação:**
   ```powershell
   mvn clean package
   ```
2. **Deploy no Tomcat:**
   Copiar o artefacto gerado `target/ROOT.war` para a pasta `webapps` do Tomcat:
   ```powershell
   Copy-Item target\ROOT.war "$env:CATALINA_HOME\webapps\ROOT.war" -Force
   ```
3. **Iniciar o Tomcat:**
   ```powershell
   & "$env:CATALINA_HOME\bin\startup.bat"
   ```
4. Aceder no navegador a `http://localhost:8080/`.

> ⚠️ **Nota de Configuração:** A ligação à base de dados e credenciais do Tomcat Manager devem ser definidas em `src/main/webapp/META-INF/context.xml` (nunca versionado no Git). Consulta o [Guia de Instalação](docs/INSTALACAO-E-DEPLOY.md#14-ligar-a-plataforma-à-bd-jndi) para ver o modelo.

---

## 👨‍💻 Autor

* **Adriano Tchiloya**
* Cadeira: Programação IV (Ano Lectivo 2026/27)
* Repositório: [plataforma-sos](https://github.com/bragadossantos/plataforma-sos)

