# Tarefas do professor

Uma ficha por tarefa, criada a partir de [`_MODELO.md`](_MODELO.md). Nome: `TNN-<slug>.md` (ex.: `T01-prototipo-portal.md`).

## Índice

| Tarefa | Título | Prazo | Estado | Tag |
|---|---|---|---|---|
| [T01](T01-avaliacao1-ambiente-userdto.md) | Avaliação 1: Ambiente (IDE, Tomcat) e UserDTO | Próxima aula | Em curso | `v0.1.0` |

## Matriz de rastreabilidade

Liga cada requisito pedido pelo professor ao requisito do sistema (`docs/REQUISITOS.md`) e ao código.

| Requisito da tarefa | Requisito do sistema | Código | Teste / prova |
|---|---|---|---|
| T01-R1 (IDE + JDK) | RNF01 | `pom.xml` | Abrir no IntelliJ / VS Code |
| T01-R2 (Tomcat 10) | RNF01 | `C:\dev\tomcat10` | `startup.bat` e localhost:8080 |
| T01-R3 (Maven Base) | RNF01 | `pom.xml` | `mvn clean package` |
| T01-R4 (UserDTO) | RF01, RF02 | `ao.sos.hub.model.UserDTO` | Compilação e getters/setters |

