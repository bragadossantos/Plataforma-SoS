---
name: ambiente-tomcat
description: Instala, configura e diagnostica o ambiente da Plataforma SoS — JDK 21, Maven, Tomcat 10.1, MySQL 8, VM Ubuntu (VMware/VirtualBox), Nginx, túneis e deploy do ROOT.war. Usar para qualquer pedido de instalação, configuração, "não arranca", erros de deploy, logs do Tomcat ou alojamento.
---

# Ambiente: Tomcat, MySQL, VM e deploy

Referência completa: `docs/INSTALACAO-E-DEPLOY.md`. Esta skill diz **como** agir.

## Antes de dar comandos

1. Perguntar/confirmar **onde** o comando vai correr: Windows (PowerShell) ou VM Ubuntu (bash via SSH).
   Rotular cada bloco: `# Windows (PowerShell)` ou `# VM (bash)`.
2. Verificar o estado actual em vez de assumir. No Windows, posso correr eu:
   ```powershell
   java -version; mvn -v; $env:JAVA_HOME; $env:CATALINA_HOME
   Test-Path C:\dev\tomcat10
   curl.exe -s -o NUL -w "%{http_code}" http://localhost:8080/
   ```
   Na VM, pedir ao utilizador o output de:
   ```bash
   java -version; systemctl is-active tomcat mysql nginx; ss -tlnp | grep -E '8080|3306|80'
   ```

## Ao instalar/configurar

- Um passo de cada vez, cada passo com **comando** + **como verificar que funcionou**.
- Versões: Tomcat 10.1.x (confirmar a última no site oficial, não inventar números), JDK 21, MySQL 8.
- Passwords sempre como `TROCA-ISTO`; nunca escrever passwords reais em ficheiros versionados.
- Configuração de produção da plataforma em `/opt/tomcat/conf/Catalina/localhost/ROOT.xml`.
- Não abrir o `/manager` para fora da VM; usar túnel SSH (`ssh -L 8080:localhost:8080`).

## Ao diagnosticar

Ordem: (1) o serviço está activo? (2) a porta responde? (3) o que diz o log? (4) só depois mexer em configuração.

| Log | Caminho Windows | Caminho VM |
|---|---|---|
| Arranque/geral | `C:\dev\tomcat10\logs\catalina.*.log` | `/opt/tomcat/logs/catalina.out` |
| Erros das aplicações | `logs\localhost.*.log` | `/opt/tomcat/logs/localhost.*.log` |
| Acessos | `logs\localhost_access_log.*.txt` | idem |

Comandos úteis da API do Manager (a plataforma usa os mesmos):
```bash
curl -u sos-deployer:PASS "http://localhost:8080/manager/text/list"
curl -u sos-deployer:PASS -T app.war "http://localhost:8080/manager/text/deploy?path=/app&update=true"
curl -u sos-deployer:PASS "http://localhost:8080/manager/text/stop?path=/app"
curl -u sos-deployer:PASS "http://localhost:8080/manager/text/start?path=/app"
curl -u sos-deployer:PASS "http://localhost:8080/manager/text/undeploy?path=/app"
```
Respostas começam por `OK -` ou `FAIL -`; a plataforma deve tratar ambas.

Ver a tabela "Diagnóstico rápido" no fim de `docs/INSTALACAO-E-DEPLOY.md` para erros comuns.

## Alojamento — o que recomendar

1. Desenvolvimento: Tomcat local no Windows.
2. Demonstração: VM Ubuntu, rede *bridged*, acesso por IP na rede da sala.
3. Acesso remoto: túnel Cloudflare (rápido) ou Oracle Cloud Always Free (permanente).
4. Desaconselhar PaaS com disco efémero para guardar os `.war` submetidos.

