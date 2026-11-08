# T01 — Avaliação 1: Ambiente (IDE, Tomcat) e UserDTO

| Campo | Valor |
|---|---|
| Recebida em | 2026-11-08 |
| Prazo | Próxima aula |
| Forma de entrega | Avaliação prática em aula / Demonstração |
| Milestone | Avaliação 1 |
| Issue-mãe | #1 |
| Branch(es) | `feature/1-avaliacao1-ambiente-userdto` |
| Tag de entrega | `v0.1.0` |
| Estado | Planeamento / Em curso |

## Enunciado (literal)

> Saudações Turma! Votos de boa saúde. A próxima aula, vamos ter a primeira avaliação. Pontos que serão objeto de avaliação:
> 1. Instalação e configuração de ferramentas ( IDE, Apache Tomcate);
> 2. Desenho de classe UserDTO;

## Requisitos extraídos

| ID | Requisito | Tipo | Já existe? | Onde / Nota |
|---|---|---|---|---|
| **T01-R1** | Configuração do IDE (Antigravity IDE) integrado com JDK e Tomcat | Técnico / Ambiente | Em curso | Antigravity usado como IDE principal; configurar Maven, JDK e Tomcat |
| **T01-R2** | Instalação e execução do Apache Tomcat (Tomcat 10.1.x) | Técnico / Infra | Não | Necessário baixar o Tomcat 10.1.x e testar em `http://localhost:8080` |
| **T01-R3** | Estrutura de projecto Maven (`pom.xml`) com Jakarta EE / Servlet 6.0 | Técnico / Base | Não | Necessário criar `pom.xml` e árvore de pastas padrão Maven |
| **T01-R4** | Desenho e implementação da classe `UserDTO` | Funcional / Código | Não | Criar em `ao.sos.hub.model.UserDTO` (serializável, campos, construtores, getters/setters) |

## Dúvidas para o professor / Alinhamentos

- [ ] **Versão do Tomcat:** O professor aceita Tomcat 10.1 (Jakarta EE)? *Nota: Tomcat 10 é o padrão moderno estabelecido para o SoS.*
- [ ] **Língua/Campos de `UserDTO`:** O professor pediu especificamente "UserDTO" (em inglês). Vamos manter `UserDTO` para respeitar o enunciado literal, mapeando os atributos essenciais para a Plataforma SoS (`id`, `nome`, `email`, `numeroEstudante`, `papel`, `activo`).

## Plano de Execução

1. **Passo 1 (Ferramentas):** Baixar e configurar Apache Tomcat 10.1 e Apache Maven em `C:\dev\`.
2. **Passo 2 (Projecto Maven):** Criar o `pom.xml` oficial da aplicação com empacotamento `war`, dependências Jakarta Servlet 6.0 e JSTL 3.0.
3. **Passo 3 (UserDTO):** Implementar `UserDTO` em `src/main/java/ao/sos/hub/model/UserDTO.java` de forma limpa, defensável e bem estruturada.
4. **Passo 4 (Validação):** Compilar com Maven, implantar página de teste no Tomcat e validar arranque.

## Critérios de aceitação (como provar na avaliação)

- [ ] O comando `startup.bat` do Tomcat arranca sem erros e `http://localhost:8080` abre no navegador.
- [ ] O IDE (IntelliJ IDEA) consegue abrir o projecto Maven e reconhecer a classe `UserDTO`.
- [ ] O código compila sem erros com `mvn clean package`.
- [ ] A classe `UserDTO` cumpre as boas práticas de DTO em Java (implements `Serializable`, encapsulamento, construtores, `toString`).

## Notas para a defesa

- **O que é um DTO (Data Transfer Object)?** Um objecto simples (POJO) usado para transportar dados entre camadas (ex.: da Servlet/Controller para a JSP ou para um serviço), sem conter lógica de negócio.
- **Porquê `Serializable`?** Para permitir que o objecto possa ser armazenado em sessão HTTP (`HttpSession`) do Tomcat ou transmitido pela rede se necessário.

