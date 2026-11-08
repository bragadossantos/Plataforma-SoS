# Instalação, configuração e deploy

Três ambientes:

| Ambiente | Onde | Para quê |
|---|---|---|
| **Local** | o teu Windows | desenvolver e testar |
| **VM** | Ubuntu Server dentro do VMware/VirtualBox | demonstração na sala, colegas na mesma rede |
| **Nuvem** (opcional) | VM gratuita (Oracle Cloud) ou túnel | acesso pela internet, de qualquer sítio |

> Estado actual da tua máquina (verificado em 08/11/2026): JDK 22 instalado, Git e GitHub CLI instalados,
> **Maven e Tomcat por instalar**.

---

## 1. Ambiente local (Windows, PowerShell)

### 1.1 JDK 21 e Maven

```powershell
winget install --id EclipseAdoptium.Temurin.21.JDK -e
winget search maven          # escolhe o id do Apache Maven que aparecer e instala:
winget install --id <id-do-maven> -e
```

Se o Maven não estiver no winget: descarrega o *binary zip* em https://maven.apache.org/download.cgi,
extrai para `C:\dev\maven` e adiciona `C:\dev\maven\bin` ao `PATH`.

Abre **um PowerShell novo** e confirma:

```powershell
java -version     # deve mostrar 21.x (se mostrar 22, ajusta JAVA_HOME — ver abaixo)
mvn -v
```

Fixar o JDK 21 como padrão (ajusta o caminho à pasta real):
```powershell
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-21.0.x-hotspot", "User")
```

### 1.2 Tomcat 10.1

1. Descarrega o **64-bit Windows zip** da versão 10.1.x mais recente em https://tomcat.apache.org/download-10.cgi.
2. Extrai para `C:\dev\tomcat10`.
3. Cria o utilizador do Manager em `C:\dev\tomcat10\conf\tomcat-users.xml`, dentro de `<tomcat-users>`:

```xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<!-- tu, pelo browser -->
<user username="admin" password="TROCA-ISTO" roles="manager-gui"/>
<!-- a plataforma, pela API de texto (password diferente!) -->
<user username="sos-deployer" password="TROCA-ISTO-TAMBEM" roles="manager-script"/>
```

4. Arrancar / parar:

```powershell
$env:CATALINA_HOME = "C:\dev\tomcat10"
& "$env:CATALINA_HOME\bin\startup.bat"
# http://localhost:8080  e  http://localhost:8080/manager/html
& "$env:CATALINA_HOME\bin\shutdown.bat"
```

5. Testar a API que a plataforma vai usar:

```powershell
curl.exe -u sos-deployer:TROCA-ISTO-TAMBEM http://localhost:8080/manager/text/list
# OK - Listed applications for virtual host [localhost] ...
```

### 1.3 MySQL 8

```powershell
winget install --id Oracle.MySQL -e
```
(ou XAMPP/WAMP se já usas, desde que seja MySQL/MariaDB recente.)

```sql
CREATE DATABASE sos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'sos_app'@'localhost' IDENTIFIED BY 'TROCA-ISTO';
GRANT ALL PRIVILEGES ON sos.* TO 'sos_app'@'localhost';
```

Copia o driver `mysql-connector-j-8.x.jar` para `C:\dev\tomcat10\lib\` (necessário para o DataSource JNDI).

### 1.4 Ligar a plataforma à BD (JNDI)

Ficheiro **local, não versionado**: `src/main/webapp/META-INF/context.xml`
(o repositório só tem `context.example.xml`).

```xml
<Context>
  <Resource name="jdbc/sos" auth="Container" type="javax.sql.DataSource"
            driverClassName="com.mysql.cj.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/sos?useSSL=false&amp;serverTimezone=Africa/Luanda&amp;allowPublicKeyRetrieval=true"
            username="sos_app" password="TROCA-ISTO"
            maxTotal="20" maxIdle="5" maxWaitMillis="10000"/>

  <Environment name="sos/manager/url"      value="http://localhost:8080/manager/text" type="java.lang.String"/>
  <Environment name="sos/manager/user"     value="sos-deployer"                         type="java.lang.String"/>
  <Environment name="sos/manager/password" value="TROCA-ISTO-TAMBEM"                    type="java.lang.String"/>
  <Environment name="sos/uploads"          value="C:/dev/sos-uploads"                   type="java.lang.String"/>
</Context>
```

> `javax.sql.DataSource` está certo: é do JDK, não do Servlet, por isso não muda para `jakarta`.

### 1.5 Build e deploy local

```powershell
mvn clean package
Remove-Item -Recurse -Force "$env:CATALINA_HOME\webapps\ROOT" -ErrorAction SilentlyContinue
Copy-Item target\ROOT.war "$env:CATALINA_HOME\webapps\ROOT.war" -Force
# http://localhost:8080/
```

Logs: `C:\dev\tomcat10\logs\catalina.<data>.log` e `localhost.<data>.log`.

---

## 2. VM Ubuntu Server (VMware ou VirtualBox)

### 2.1 Criar a VM

| Parâmetro | Valor |
|---|---|
| Hipervisor | VMware Workstation Pro (gratuito para uso pessoal) ou VirtualBox |
| SO | Ubuntu Server 24.04 LTS |
| CPU / RAM / Disco | 2 vCPU / 4 GB / 30 GB |
| Rede | **Bridged** (a VM fica com IP da rede local — os colegas acedem directamente) |
| Durante a instalação | marcar **Install OpenSSH server** |

Depois de arrancar: `ip a` para ver o IP. A partir daí, liga-te do Windows com `ssh utilizador@<ip>`.

### 2.2 Pacotes base

```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y openjdk-21-jdk-headless mysql-server curl unzip ufw
java -version
```

### 2.3 Tomcat 10.1 como serviço

```bash
TOMCAT_VERSION=10.1.XX   # confirma a última em https://tomcat.apache.org/download-10.cgi
sudo useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
cd /tmp
curl -LO https://dlcdn.apache.org/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz
sudo tar xzf apache-tomcat-${TOMCAT_VERSION}.tar.gz -C /opt/tomcat --strip-components=1
sudo chown -R tomcat:tomcat /opt/tomcat
sudo mkdir -p /opt/sos/uploads && sudo chown -R tomcat:tomcat /opt/sos
```

`/etc/systemd/system/tomcat.service`:

```ini
[Unit]
Description=Apache Tomcat 10.1 (Plataforma SoS)
After=network.target mysql.service

[Service]
Type=forking
User=tomcat
Group=tomcat
Environment="JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64"
Environment="CATALINA_HOME=/opt/tomcat"
Environment="CATALINA_BASE=/opt/tomcat"
Environment="CATALINA_PID=/opt/tomcat/temp/tomcat.pid"
Environment="CATALINA_OPTS=-Xms512M -Xmx2048M -Duser.timezone=Africa/Luanda -Dfile.encoding=UTF-8"
ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

```bash
sudo systemctl daemon-reload
sudo systemctl enable --now tomcat
systemctl status tomcat
sudo tail -f /opt/tomcat/logs/catalina.out
```

Configura `/opt/tomcat/conf/tomcat-users.xml` como em 1.2 (passwords fortes!).
O Manager só aceita pedidos de `localhost` por omissão — **mantém assim**: a plataforma chama-o
de dentro da própria VM. Para usares o `manager/html` a partir do Windows, faz um túnel SSH:

```powershell
ssh -L 8080:localhost:8080 utilizador@<ip-da-vm>
# depois abre http://localhost:8080/manager/html no Windows
```

### 2.4 MySQL na VM

```bash
sudo mysql
```
```sql
CREATE DATABASE sos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'sos_app'@'localhost' IDENTIFIED BY 'PASSWORD-FORTE';
GRANT ALL PRIVILEGES ON sos.* TO 'sos_app'@'localhost';

-- por cada colega (exemplo: biblioteca)
CREATE DATABASE sos_biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'biblioteca'@'localhost' IDENTIFIED BY 'PASSWORD-DO-COLEGA';
GRANT ALL PRIVILEGES ON sos_biblioteca.* TO 'biblioteca'@'localhost';
FLUSH PRIVILEGES;
```

```bash
# importar o script do colega
mysql -u biblioteca -p sos_biblioteca < biblioteca.sql
```

Driver JDBC para o Tomcat:
```bash
sudo curl -L -o /opt/tomcat/lib/mysql-connector-j.jar \
  https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar
sudo chown tomcat:tomcat /opt/tomcat/lib/mysql-connector-j.jar
sudo systemctl restart tomcat
```

### 2.5 Firewall

```bash
sudo ufw allow OpenSSH
sudo ufw allow 8080/tcp      # ou 80/443 se usares Nginx (2.7)
sudo ufw enable
```

### 2.6 Enviar a plataforma do Windows para a VM

```powershell
mvn clean package
scp target\ROOT.war utilizador@<ip-da-vm>:/tmp/
ssh utilizador@<ip-da-vm> "sudo rm -rf /opt/tomcat/webapps/ROOT && sudo mv /tmp/ROOT.war /opt/tomcat/webapps/ && sudo chown tomcat:tomcat /opt/tomcat/webapps/ROOT.war"
```

O `context.xml` de produção (com as passwords da VM) fica em
`/opt/tomcat/conf/Catalina/localhost/ROOT.xml` — assim não está dentro do `.war` nem no Git.

### 2.7 (Recomendado) Nginx na porta 80

```bash
sudo apt install -y nginx
```
`/etc/nginx/sites-available/sos`:
```nginx
server {
    listen 80;
    server_name _;
    client_max_body_size 60M;

    location /manager { deny all; }

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```
```bash
sudo ln -s /etc/nginx/sites-available/sos /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
sudo nginx -t && sudo systemctl reload nginx
sudo ufw allow 'Nginx Full' && sudo ufw delete allow 8080/tcp
```

### 2.8 Backups

```bash
sudo crontab -e
# todos os dias às 02:00
0 2 * * * mysqldump --all-databases | gzip > /opt/sos/backup-$(date +\%F).sql.gz
```

---

## 3. Acesso pela internet (opcional)

| Opção | Custo | Prós | Contras |
|---|---|---|---|
| **Túnel Cloudflare** a partir da VM local | grátis | 1 comando, sem abrir portas no router | só funciona com a VM ligada; URL aleatório no modo rápido |
| **Oracle Cloud Always Free** (VM Ubuntu) | grátis | VM permanente com IP público, recursos generosos | registo pede cartão; a configuração é a mesma da secção 2 |
| VPS pago (Hetzner, DigitalOcean, Contabo…) | ~4–6 USD/mês | simples e estável | pago |
| PaaS (Railway, Render) | grátis/pago | deploy fácil | disco efémero: os `.war` submetidos perdem-se sem volume persistente; **não recomendado** para este projecto |

Túnel rápido (demo):
```bash
# na VM
curl -L -o cloudflared.deb https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64.deb
sudo dpkg -i cloudflared.deb
cloudflared tunnel --url http://localhost:80
# mostra um URL https://<algo>.trycloudflare.com para partilhar com a turma
```

**Recomendação:** desenvolver local → demonstrar na VM em modo bridged → se o professor quiser
acesso remoto, replicar a mesma VM na Oracle Cloud (os comandos da secção 2 são iguais).

---

## 4. Diagnóstico rápido

| Sintoma | Causa provável | Ver |
|---|---|---|
| 404 em todo o sistema do colega | deploy falhou | `manager/text/list`, `logs/localhost.*.log` |
| `ClassNotFoundException: javax.servlet...` | sistema feito para Tomcat 9 | guia de integração §2.1 |
| CSS/links partidos só no servidor | caminhos absolutos | guia de integração §3 |
| `Communications link failure` | MySQL parado ou credenciais erradas | `systemctl status mysql` |
| `UnsupportedClassVersionError` | compilado com Java > 21 | recompilar com `release 17/21` |
| `403` no `/manager/text` | utilizador sem `manager-script` ou pedido não vem de localhost | `tomcat-users.xml`, `webapps/manager/META-INF/context.xml` |
| Acentos estragados | codificação | `-Dfile.encoding=UTF-8`, `<%@ page pageEncoding="UTF-8" %>`, BD `utf8mb4` |

