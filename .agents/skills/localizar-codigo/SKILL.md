---
name: localizar-codigo
description: Mostra ao utilizador onde um requisito, funcionalidade ou comportamento já está implementado no código da Plataforma SoS, com links para ficheiros e linhas, e explica o fluxo do pedido de ponta a ponta. Usar quando o utilizador perguntar "onde está", "já fiz isto?", "como funciona X", ou quando um requisito de tarefa já existir.
---

# Localizar código e explicar

## Passos

1. **Traduzir o requisito em pistas de pesquisa:** URL (`/admin/sistemas`), nome de servlet,
   nome de JSP, tabela/coluna, texto visível na interface, nome de método.
2. **Pesquisar** no repositório (`src/main/java`, `src/main/webapp`, `db/`). Começar pelo `@WebServlet`
   ou pelo texto visível e seguir as chamadas.
3. **Montar o fluxo de ponta a ponta**, na ordem em que um pedido HTTP o percorre:

   ```
   Página/Formulário (JSP)  →  Filter(s)  →  Servlet (doGet/doPost)  →  Service  →  DAO  →  Tabela
                                                    ↓
                                              JSP de resposta
   ```

4. **Responder neste formato:**

   ```markdown
   **Estado:** implementado | parcial | não existe

   **Onde está**
   1. Formulário — [nome.jsp](file:///caminho#L10-L25)
   2. Controller — [`XServlet.doPost`](file:///caminho#L30-L58)
   3. Regra — [`XService.metodo`](file:///caminho#L12-L40)
   4. BD — [`XDAO.inserir`](file:///caminho#L20-L35) → tabela `x`

   **Como funciona** (3–6 frases em linguagem simples)

   **Como testar** (passos no browser)

   **O que dizer na defesa** (2–3 frases)

   **O que falta** (só se for parcial)
   ```

5. Mostrar excertos curtos de código apenas para as linhas-chave (máx. ~15 linhas cada).

## Regras

- Links sempre com caminho absoluto e `#Lx-Ly` correctos — verificar as linhas lendo o ficheiro, não adivinhar.
- Não reescrever nem "melhorar" código durante a explicação, a menos que o utilizador peça.
- Se houver duas implementações do mesmo requisito, apontar as duas e sinalizar a duplicação.

