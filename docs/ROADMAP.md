# Roadmap e ideias

Fases pensadas para encaixar nas entregas do professor. A ordem pode mudar conforme o que ele pedir —
a ficha de cada tarefa (`docs/tarefas/`) é que manda.

## Fase 0 — Fundação
- Repositório, documentação, labels e milestones.
- Projecto Maven `war` com Servlet/JSP/JSTL a correr no Tomcat local (página "olá" no ROOT).
- Ligação MySQL via JNDI + script `db/schema.sql`.

## Fase 1 — MVP (o mínimo para ser um SoS)
- RF01–RF04 contas e papéis.
- RF10–RF11 portal e detalhe.
- RF20–RF23 submissão, validação, deploy, stop/start/remover.
- RF30–RF31 health check e estado no portal.

## Fase 2 — Robustez
- Histórico de versões e actualização (RF24), BD por colega (RF26).
- Sincronização com `manager/text/list` (RF32), auditoria (RF33).
- Duas instâncias Tomcat (plataforma isolada dos sistemas dos colegas).
- Nginx + domínio/túnel para acesso externo.

## Fase 3 — Comportamento emergente (o que valoriza o tema)
Ideias para mostrar que o todo é mais do que a soma das partes:

| Ideia | Esforço | Porque impressiona |
|---|---|---|
| **Painel de estado da turma** — grelha com todos os sistemas, tempo de resposta e uptime das últimas 24 h | médio | é o "centro de operações" do SoS |
| **API pública `/api/sistemas`** (JSON) com o catálogo | baixo | outros sistemas podem descobrir-se uns aos outros |
| **Barra comum opcional** — um pequeno script `sos-bar.js` que os colegas incluem e mostra "← Voltar à Plataforma SoS" | baixo | dá identidade de "ecossistema" |
| **Diretório de serviços**: cada sistema declara em `sos.properties` endpoints que oferece (ex.: `/api/livros`) e a plataforma lista-os | médio | interoperabilidade real entre sistemas dos colegas |
| **Notificações**: email/registo quando um sistema cai | médio | monitorização a sério |
| **Rollback com um clique** | médio | gestão de versões |
| Sistemas em contentores Docker isolados | alto | isolamento total; só se sobrar tempo |
| Login único (SSO) via token assinado | alto | integração forte; arriscado para o prazo |

## Perguntas a fazer ao professor cedo
1. Os colegas usam Tomcat 9 (`javax`) ou 10 (`jakarta`)? Posso impor Tomcat 10?
2. Todos usam MySQL? Há quem use outro SGBD?
3. A demonstração final é na rede da universidade, no meu portátil, ou tem de estar online?
4. O repositório pode ser público? Ele quer ser adicionado como colaborador?
5. Há requisitos obrigatórios de relatório (UML, casos de uso, manual)?

