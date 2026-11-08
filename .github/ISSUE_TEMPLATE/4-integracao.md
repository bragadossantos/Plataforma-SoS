---
name: Pedido de integração (colegas)
about: Pedir para o teu sistema entrar na Plataforma SoS
title: "Integração: <nome do sistema>"
labels: integracao
---

Antes de abrir: lê `docs/GUIA-INTEGRACAO-COLEGAS.md`.

- **Nome do sistema:**
- **Slug pretendido:**
- **Autores (nome e nº):**
- **Usa base de dados?** sim / não — SGBD:
- **Java / Tomcat com que testaste:**
- **Usa `jakarta.servlet`?** sim / não

### Checklist do guia
- [ ] `.war` testado num Tomcat 10.1 com contexto `/<slug>`
- [ ] Sem `javax.servlet`
- [ ] Links com context path
- [ ] `WEB-INF/sos.properties` preenchido
- [ ] Script `.sql` (se aplicável)

### Dúvidas

