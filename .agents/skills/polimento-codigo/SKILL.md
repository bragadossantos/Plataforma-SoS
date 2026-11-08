---
name: polimento-codigo
description: Revê e limpa código Java/JSP/SQL da Plataforma SoS para parecer escrito por um estudante cuidadoso e não gerado — remove comentários óbvios, nomes genéricos, tratamento de erros decorativo, abstracções desnecessárias e padrões típicos de IA, mantendo o comportamento. Usar antes de cada commit relevante, PR ou entrega, ou quando o utilizador pedir "polir", "limpar", "não parecer IA".
---

# Polimento de código

Objectivo: código **simples, consistente e defensável**. O autor tem de conseguir explicar cada linha
na defesa; se não consegue, a linha é simplificada ou explicada a ele — nunca escondida.

## Âmbito

Por omissão, só os ficheiros alterados desde a última tag: `git diff --name-only $(git describe --tags --abbrev=0)..HEAD`
(se não houver tags, os ficheiros alterados em relação a `develop`). Não tocar no resto sem pedir.

## Checklist — o que procurar e corrigir

### Comentários
- ❌ Comentários que repetem o código: `// incrementa o contador`, `// construtor`, `// getters e setters`.
- ❌ Javadoc em todos os getters/setters e em métodos triviais.
- ❌ Cabeçalhos decorativos (`// ===== SECÇÃO =====`), comentários em inglês misturados com código em português.
- ❌ `// TODO: implement`, `// Add your logic here`, `// Example usage`, `// This method handles...`.
- ✅ Manter/escrever só comentários que explicam **porquê** (uma decisão, uma limitação do Tomcat, um caso estranho).
- ✅ Javadoc curto apenas em classes de serviço/infra e métodos públicos não óbvios.

### Nomes
- ❌ Genéricos: `data`, `result`, `obj`, `temp`, `item`, `handleRequest`, `processData`, `Manager`/`Helper`/`Utils` para tudo.
- ❌ Mistura de línguas no mesmo conceito (`listarSystems`, `getUtilizador` + `fetchUser`).
- ✅ Domínio em português e consistente: `sistema`, `submissao`, `versaoActiva`, `listarPorEstado()`.
- ✅ Booleanos legíveis: `estaActivo`, `temBaseDados`.

### Estrutura
- ❌ Interfaces com uma única implementação "para o futuro" (`ISistemaService` + `SistemaServiceImpl`) — a menos que o professor exija.
- ❌ Padrões desnecessários (Factory, Builder, Strategy) para 2 casos.
- ❌ Métodos de 80 linhas; classes "Deus".
- ❌ Código morto, imports não usados, variáveis nunca lidas, `System.out.println` de debug.
- ✅ Métodos até ~30 linhas, early return em vez de `if` aninhados.
- ✅ Constantes com nome em vez de números/strings mágicas (`TAMANHO_MAX_WAR`).

### Erros
- ❌ `catch (Exception e) { e.printStackTrace(); }` ou `catch` vazio.
- ❌ `try/catch` à volta de tudo "por segurança"; mensagens genéricas ("An error occurred").
- ✅ Apanhar a excepção específica onde se sabe o que fazer; caso contrário propagar.
- ✅ No servlet: registar com `getServletContext().log(...)` (ou `java.util.logging`) e mostrar mensagem útil em português ao utilizador.

### Logs e strings
- ❌ Emojis, `✅ Success!`, `🚀 Starting...`, pontos de exclamação a mais, inglês nas mensagens ao utilizador.
- ✅ Mensagens curtas e concretas: `"Sistema 'biblioteca' implantado (v1.2.0)."`.

### Formatação e consistência
- Indentação de 4 espaços, chavetas no estilo Java padrão, uma linha em branco entre métodos.
- Ordem na classe: constantes → campos → construtores → métodos públicos → privados.
- Mesmo estilo em todos os DAOs (mesma forma de abrir ligação, mapear `ResultSet`, nomear métodos).
- JSP: sem scriptlets, indentação consistente, EL/JSTL, `<c:out>` para dados do utilizador.
- SQL: palavras-chave em maiúsculas, nomes `snake_case`, um script de schema ordenado.

### Sinais de "gerado" a eliminar
- Variações desnecessárias do mesmo código copiadas 5 vezes → extrair método.
- Validações que nunca podem falhar (verificar `null` de algo que acabou de ser criado).
- Over-engineering para o tamanho do projecto (DTO + Entity + Mapper para uma tabela de 4 colunas).
- Ficheiros `README` ou comentários a "vender" o código.

## Procedimento

1. Listar os ficheiros no âmbito.
2. Para cada ficheiro, aplicar a checklist; **não alterar comportamento**.
3. Compilar (`mvn -q clean package`) e, se existir, testar o fluxo afectado no Tomcat.
4. Resumo ao utilizador: tabela `ficheiro → o que mudou`, e uma lista de **pontos que ele deve
   conseguir explicar** (ex.: "porque é que o DAO usa try-with-resources").
5. Commit separado: `refactor(<âmbito>): simplifica ...` ou `style: ...`.

