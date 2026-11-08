---
name: nova-tarefa
description: Recebe um enunciado/tarefa do professor de Programação IV, transforma-o numa ficha em docs/tarefas/, extrai requisitos numerados, cruza com o que já existe no código, cria issues/branch e planeia a implementação. Usar sempre que o utilizador colar um enunciado, pedido do professor, ou disser "nova tarefa" / "o professor pediu".
---

# Nova tarefa do professor

Objectivo: nunca começar a programar sem saber exactamente o que o professor pediu, o que já existe
e como se vai provar que está feito.

## Passos

1. **Guardar o enunciado literal.** Copiar o modelo `docs/tarefas/_MODELO.md` para
   `docs/tarefas/TNN-<slug>.md` (NN = próximo número livre). Colar o enunciado sem resumir.

2. **Extrair requisitos.** Ler frase a frase. Cada verbo de acção ("deve permitir", "implementar",
   "apresentar", "entregar") vira um requisito `TNN-Rk`. Classificar: funcional, técnico (ex.: "usar
   Tomcat", "usar MVC"), ou documento/entrega (ex.: "relatório", "diagrama").
   Requisitos implícitos (ex.: "login" implica logout e protecção de páginas) entram marcados como *implícito*.

3. **Cruzar com o código existente.** Para cada requisito, procurar no repositório (grep por
   palavras-chave, servlets, JSPs, tabelas). Preencher a coluna "Já existe?" com `sim`/`parcial`/`não`
   e o link `ficheiro#Lx-Ly`. Para os que já existem, seguir a skill `localizar-codigo`.

4. **Mapear para `docs/REQUISITOS.md`.** Se o requisito corresponde a um RFxx, referir. Se é novo,
   propor um RF novo (não editar o documento sem confirmação).

5. **Dúvidas.** Listar ambiguidades concretas para o aluno confirmar com o professor. Não inventar respostas.

6. **Plano e critérios de aceitação.** Passos pequenos, um por requisito quando possível.
   Cada critério de aceitação é um teste manual executável no Tomcat local.

7. **Parar e mostrar ao utilizador:** tabela de requisitos, o que já existe, o que falta, dúvidas,
   estimativa de esforço versus prazo. **Esperar confirmação** antes de escrever código.

8. **Depois de confirmado** (seguir skill `fluxo-git`):
   - issue-mãe com label `tarefa-professor` e o enunciado; uma issue por requisito que exige código;
   - milestone da entrega;
   - branch `feature/<nº>-<slug>` a partir de `develop`;
   - actualizar o índice e a matriz em `docs/tarefas/README.md`.

9. **Implementar** só o âmbito da tarefa. Commits por requisito (`Refs #nº`).

10. **Fechar:** correr `polimento-codigo` e `polimento-interface`, marcar critérios de aceitação,
    preencher "Notas para a defesa", PR, merge, tag (skill `fluxo-git`).

## Regras

- Se o prazo for curto, priorizar o que o professor vai avaliar explicitamente; o resto vai para "Limitações conhecidas".
- Se o enunciado contradiz uma decisão do `AGENTS.md` (ex.: pede Tomcat 9), o enunciado ganha —
  avisar o utilizador e actualizar o `AGENTS.md` depois de confirmado.
- Nunca apagar funcionalidades de tarefas anteriores para cumprir a actual sem avisar.

