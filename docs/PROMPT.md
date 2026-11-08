# Prompts prontos

Copia e cola no chat. Substitui o que está entre `<...>`.
O contexto geral já é carregado automaticamente pelo `AGENTS.md`, por isso estes prompts são curtos.

---

## 1. Prompt mestre (início de sessão / modelo novo / outra ferramenta de IA)

Usa este quando abrires o projecto noutra ferramenta que não lê o `AGENTS.md`, ou quando quiseres
"reiniciar" o contexto.

```text
Estás a trabalhar comigo no meu projecto semestral de Programação IV: a "Plataforma SoS"
(Sistema de Sistemas). É uma aplicação web em Java 21 com Jakarta Servlets + JSP + JSTL,
build com Maven, a correr em Apache Tomcat 10.1, com MySQL 8. A plataforma fica no contexto
ROOT do Tomcat e serve para albergar os sistemas web (.war) dos meus colegas: recebe o upload,
valida o .war contra um contrato de integração, faz deploy pela API de texto do Tomcat Manager,
mostra todos os sistemas num portal e monitoriza se estão no ar.

Regras:
- Lê primeiro AGENTS.md e os ficheiros em docs/ (REQUISITOS, GUIA-INTEGRACAO-COLEGAS,
  INSTALACAO-E-DEPLOY, GIT-WORKFLOW) e o estado actual do código.
- O trabalho é feito por etapas: cada tarefa do professor tem uma ficha em docs/tarefas/.
  Implementa apenas o que a tarefa actual pede.
- Se algo já estiver feito, mostra-me onde está no código (ficheiro e linhas) em vez de refazer.
- MVC: Servlet -> Service -> DAO (JDBC/PreparedStatement). JSP em WEB-INF/views, sem scriptlets.
- Usa jakarta.*, nunca javax.*.
- Responde em português (PT/Angola). Dá comandos prontos para PowerShell (Windows) e bash (Ubuntu).
- Código e interface com aspecto de trabalho feito por uma pessoa: nada de comentários óbvios,
  emojis, gradientes roxos, textos de marketing genéricos.
- Tenho de conseguir explicar tudo na defesa: prefere simples e legível.

Confirma que percebeste resumindo em 5 linhas o estado actual do projecto e o próximo passo.
```

---

## 2. Nova tarefa do professor

```text
Nova tarefa do professor. Usa a skill nova-tarefa.

Prazo: <data e hora>
Forma de entrega: <ex.: link do GitHub + .war / apresentação / relatório>

Enunciado (copiado tal e qual):
"""
<cola aqui o enunciado>
"""

Antes de escrever código: cria a ficha docs/tarefas/TNN-<slug>.md, lista os requisitos
numerados, diz-me o que já existe no código, o que falta, e as dúvidas que tenho de
esclarecer com o professor. Espera pela minha confirmação.
```

## 3. Implementar depois de aprovado

```text
Aprovado. Implementa a tarefa TNN seguindo a ficha. Cria a branch, faz commits pequenos
por requisito, e no fim corre polimento-codigo e polimento-interface. Mostra-me como testar
localmente no Tomcat.
```

## 4. "Isto já está feito? Onde?"

```text
Usa a skill localizar-codigo. O professor pede: "<requisito>".
Já está implementado? Mostra-me os ficheiros e linhas exactas, explica o fluxo do pedido
(do formulário/URL até à base de dados) e o que eu devo dizer na defesa.
```

## 5. Ambiente / instalação

```text
Usa a skill ambiente-tomcat. Quero <instalar Tomcat no Windows | preparar a VM Ubuntu |
fazer deploy da plataforma na VM | expor a VM à internet>. Dá-me os comandos passo a passo
e diz-me como verificar que cada passo funcionou.
```

## 6. Validar o sistema de um colega

```text
Usa a skill validar-sistema-colega. O ficheiro está em <caminho/para/sistema.war>
(colega: <nome>, slug pretendido: <slug>). Diz-me se cumpre o contrato e, se não cumprir,
escreve a mensagem que lhe devo enviar com o que tem de corrigir.
```

## 7. Polimento antes da entrega

```text
Vamos fechar a entrega TNN. Corre polimento-codigo em todo o código alterado desde a última
tag e polimento-interface nas páginas tocadas. Lista o que mudaste. Depois prepara o PR,
a tag e as notas de release com a skill fluxo-git.
```

## 8. Preparar a defesa

```text
Prepara-me para a defesa da entrega TNN: 10 perguntas prováveis do professor sobre o código
e a arquitectura, com respostas curtas baseadas no código real (com links para os ficheiros).
Inclui perguntas sobre "porque é que isto é um Sistema de Sistemas".
```

