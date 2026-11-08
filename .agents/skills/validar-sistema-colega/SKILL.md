---
name: validar-sistema-colega
description: Inspecciona o .war (ou projecto) de um colega e verifica se cumpre o contrato de integração da Plataforma SoS (Jakarta vs javax, versão do bytecode, caminhos absolutos, sos.properties, BD, bibliotecas). Produz um relatório e a mensagem a enviar ao colega. Usar quando o utilizador receber o sistema de um colega ou perguntar se um sistema "vai rodar" na plataforma.
---

# Validar sistema de um colega

Contrato: `docs/GUIA-INTEGRACAO-COLEGAS.md`. Validação automática equivalente: `docs/REQUISITOS.md` §6.

## Passos (Windows, PowerShell)

1. **Extrair para uma pasta temporária** (nunca dentro de `src/`):
   ```powershell
   $war = "<caminho>\sistema.war"
   $dst = "$env:TEMP\sos-validar\" + [IO.Path]::GetFileNameWithoutExtension($war)
   Remove-Item -Recurse -Force $dst -ErrorAction SilentlyContinue
   Add-Type -AssemblyName System.IO.Compression.FileSystem
   [IO.Compression.ZipFile]::ExtractToDirectory($war, $dst)
   "{0:N1} MB" -f ((Get-Item $war).Length / 1MB)
   ```
2. **Estrutura:** existe `WEB-INF/`? `WEB-INF/web.xml` ou servlets anotados? página inicial?
3. **Jakarta vs javax:**
   ```powershell
   Get-ChildItem $dst -Recurse -Filter *.class | Select-String -Pattern "javax/servlet" -SimpleMatch -List | Select-Object -First 5 Path
   Select-String -Path "$dst\WEB-INF\web.xml" -Pattern "jakarta.ee|java.sun.com|xmlns.jcp.org"
   Get-ChildItem "$dst\WEB-INF\lib" -ErrorAction SilentlyContinue | Select-Object Name
   ```
   Procurar também `javax.servlet.jsp.jstl` / `jstl-1.2.jar` (JSTL antigo) e `servlet-api.jar` dentro de `lib` (não deve estar).
4. **Versão do bytecode** (major ≤ 65 = Java 21):
   ```powershell
   Get-ChildItem $dst -Recurse -Filter *.class | Select-Object -First 20 | ForEach-Object {
     $b = [IO.File]::ReadAllBytes($_.FullName); [PSCustomObject]@{ Classe = $_.Name; Major = $b[6]*256 + $b[7] }
   } | Sort-Object Major -Unique
   ```
5. **Caminhos absolutos** nas JSP/HTML/JS:
   ```powershell
   Get-ChildItem $dst -Recurse -Include *.jsp,*.html,*.js |
     Select-String -Pattern 'localhost:\d+','(href|src|action)="/(?!/)','sendRedirect\("/' | Select-Object -First 30
   ```
   (os `sendRedirect` estão nos `.class`; se houver código-fonte, procurar também nos `.java`.)
6. **Manifesto:** ler `WEB-INF/sos.properties`; verificar campos obrigatórios (`sos.nome`, `sos.slug`, `sos.versao`, `sos.autores`, `sos.descricao`) e slug válido/não reservado.
7. **Base de dados:** driver em `WEB-INF/lib` (`mysql-connector-j*`)? onde está a configuração (`db.properties`, classe `Conexao`)? URL aponta para `localhost:3306/sos_<slug>`? Existe script `.sql` sem `CREATE DATABASE`/`USE`?
8. **(Opcional) Teste real** no Tomcat local, com o contexto final:
   ```powershell
   curl.exe -u sos-deployer:PASS -T $war "http://localhost:8080/manager/text/deploy?path=/<slug>&update=true"
   curl.exe -s -o NUL -w "%{http_code}" http://localhost:8080/<slug>/
   ```
   Ver `logs\localhost.*.log` se falhar. No fim, `undeploy`.

## Saída

1. Tabela V1–V8 com ✅ / ⚠️ / ❌ e evidência (ficheiro/linha).
2. Veredicto: **Pronto** · **Pronto com avisos** · **Precisa de correcções**.
3. **Mensagem para o colega**, em português, curta, educada, com a lista numerada do que corrigir e
   o excerto de código/configuração certo para cada ponto (reaproveitar exemplos do guia de integração).

## Regras

- Não modificar o `.war` do colega. Se for preciso converter `javax`→`jakarta`, propor ao utilizador as
  opções (colega corrige / pasta `webapps-javaee` / ferramenta oficial *Apache Tomcat Migration Tool for Jakarta EE*).
- Apagar a pasta temporária no fim.

