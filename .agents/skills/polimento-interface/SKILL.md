---
name: polimento-interface
description: Revê e ajusta as páginas JSP/CSS da Plataforma SoS para terem identidade própria e aspecto de produto real/institucional, evitando a estética genérica de interfaces geradas por IA (gradientes roxos, emojis, cartões iguais, textos de marketing). Usar ao criar ou alterar qualquer ecrã, antes de entregas, ou quando o utilizador pedir "melhorar o design" ou "não parecer IA".
---

# Polimento de interface

A plataforma é uma **ferramenta de administração e um portal académico**, não uma landing page de startup.
Referências de tom: painéis de serviços públicos, GitHub, sistemas académicos bem feitos. Denso, claro, sóbrio.

## Sistema visual (fixo — está em `static/css/base.css`)

Definir uma vez em variáveis CSS e reutilizar; nunca cores soltas no HTML.

```css
:root {
  /* ajustar às cores da universidade, se existirem */
  --cor-fundo: #f6f5f2;        /* quase branco quente, não #fff puro */
  --cor-superficie: #ffffff;
  --cor-texto: #1d1f21;
  --cor-texto-2: #5b6168;
  --cor-borda: #dcdad5;
  --cor-marca: #0f4c5c;        /* UM acento, sóbrio */
  --cor-ok: #2e7d32; --cor-aviso: #b26a00; --cor-erro: #b3261e;
  --raio: 4px;                 /* cantos discretos */
  --esp-1: 4px; --esp-2: 8px; --esp-3: 12px; --esp-4: 16px; --esp-6: 24px; --esp-8: 32px;
  --fonte: "Source Sans 3", "Segoe UI", system-ui, sans-serif;
  --fonte-mono: "JetBrains Mono", Consolas, monospace;
}
```

Fontes servidas localmente (`static/fonts/`) — a sala pode não ter internet.

## Evitar (sinais típicos de interface gerada)

| ❌ Evitar | ✅ Fazer em vez disso |
|---|---|
| Gradientes roxo/azul, fundos com "blobs", glassmorphism | Fundo liso, uma cor de marca, contraste forte |
| Hero centrado "Bem-vindo ao futuro dos sistemas" + 3 cartões com ícones | Ir directo ao conteúdo: a lista de sistemas é a página inicial |
| Emojis como ícones (🚀 ✨ 📊) | Sem ícones, ou um conjunto SVG único e coerente (ex.: Lucide/Tabler, locais) |
| Tudo em cartões com `rounded-2xl` e sombra grande | Tabelas e listas para dados; cartões só onde fazem sentido; sombras subtis ou nenhumas |
| Textos genéricos/marketing ("Potencie", "Descubra", "Sem esforço") | Texto funcional e específico: "Sistemas da turma de Programação IV — 2026/27" |
| Lorem ipsum, "John Doe", dados falsos perfeitos | Dados realistas: nomes angolanos, slugs reais, datas no formato `08/11/2026 10:20` |
| Botões todos iguais e grandes | Hierarquia: 1 primário por ecrã, secundários discretos, acções destrutivas a vermelho com confirmação |
| Animações em tudo, fade-in ao fazer scroll | Transições curtas só em estados (hover/focus), ≤150 ms |
| Inter/Poppins por defeito, títulos enormes | Tipografia definida acima, escala contida (14/16/20/24/32 px) |
| Estados vazios em branco ou com ilustração genérica | Estado vazio útil: "Ainda não submeteste nenhum sistema. [Submeter o primeiro]" |
| Ícone + título + descrição repetidos em grelha 3×N | Variar a composição conforme o conteúdo |

## Coisas que dão ar de produto real

- **Cabeçalho** com nome da plataforma, curso/turma e utilizador ligado; rodapé com versão (`v0.2.0`) e ano lectivo.
- **Estados com etiqueta de cor + texto** (`● No ar`, `● Em baixo`) — nunca só cor (acessibilidade).
- **Tabelas** com cabeçalho fixo, números alinhados à direita, datas relativas com a absoluta no `title`.
- **Mensagens flash** depois de acções ("Sistema 'farmacia' parado."), não `alert()`.
- **Formulários** com `label` visível, ajuda curta por baixo do campo, erros junto ao campo, valores preservados após erro.
- **Breadcrumb** nas páginas internas: `Admin › Sistemas › biblioteca`.
- **Foco visível** (`:focus-visible`), contraste AA, funciona a 360 px de largura.
- Microcopy em português de Angola/Portugal: "ficheiro", "utilizador", "Guardar", "Cancelar", "Submeter".
- Favicon e `<title>` específicos por página (`Biblioteca · Plataforma SoS`).

## Procedimento

1. Listar as JSP/CSS tocadas na tarefa.
2. Verificar contra as duas tabelas acima e contra as variáveis CSS (nada de cores/espaçamentos fora da escala).
3. Ver a página a 1280 px e 360 px (pedir captura ao utilizador ou abrir no browser, se disponível).
4. Corrigir; manter HTML semântico (`header`, `nav`, `main`, `table`, `form`, `button`).
5. Resumo: o que mudou por página e porquê (uma linha cada).

