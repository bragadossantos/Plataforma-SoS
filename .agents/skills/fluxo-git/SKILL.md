---
name: fluxo-git
description: Aplica o fluxo Git/GitHub do projecto — branches por funcionalidade a partir de develop, Conventional Commits em português, issues/labels/milestones, PRs, tags SemVer e releases por entrega, usando git e gh CLI. Usar ao começar ou terminar trabalho, fazer commit, abrir PR, preparar entrega, criar issues ou verificar o estado do versionamento.
---

# Fluxo Git

Referência: `docs/GIT-WORKFLOW.md`.

## Ao começar trabalho

```powershell
git status                     # árvore limpa?
git switch develop; git pull
gh issue list --milestone "<Entrega N>"
git switch -c feature/<nº>-<descricao>
```
Se não houver issue para o trabalho, criá-la primeiro:
`gh issue create --title "TNN-Rk: ..." --label feature --milestone "Entrega N" --body "..."`.

## Durante

- Commits pequenos, uma ideia cada, formato `tipo(âmbito): resumo` + `Refs #nº`.
- Antes de cada commit: `git diff --staged` e confirmar que não entram `target/`, `.war`, `context.xml` real, passwords.
- Propor a mensagem de commit ao utilizador; não fazer `push` nem `merge` sem ele confirmar.

## Ao terminar uma funcionalidade

```powershell
mvn -q clean package          # tem de compilar
git push -u origin HEAD
gh pr create --base develop --title "<tipo>(<âmbito>): ..." --body-file <ficheiro> --label <label> --milestone "Entrega N"
```
Corpo do PR segundo `.github/PULL_REQUEST_TEMPLATE.md`, com `Closes #nº` e passos de teste.

## Ao fechar uma entrega

1. Todos os PRs da milestone integrados em `develop`; ficha da tarefa com critérios marcados.
2. `git switch main; git pull; git merge --no-ff develop -m "release: entrega N"`.
3. `mvn clean package`; `git tag -a v0.N.0 -m "Entrega N — <resumo>"`; `git push origin main --tags`.
4. `gh release create v0.N.0 target\ROOT.war --title "Entrega N" --notes "<notas>"`.
5. Fechar a milestone; actualizar índice em `docs/tarefas/README.md`.

## Auditoria do versionamento (quando pedirem "verifica o git")

```powershell
git log --oneline --graph --all -30
git branch -a --merged develop
gh issue list --state open
gh pr list --state all -L 10
git tag -l
```
Reportar: commits fora do padrão, branches já integradas por apagar, issues sem milestone,
trabalho feito directamente em `main`, ficheiros sensíveis no histórico (`git log --all -- '*context.xml'`).

## Regras

- Nunca `push --force` em `main`/`develop`. Nunca reescrever histórico já publicado sem pedir.
- Nunca commitar credenciais. Se acontecer: avisar para trocar a password imediatamente.
- Mensagens sem emojis, sem "update"/"changes", sem referências a ferramentas de IA.

