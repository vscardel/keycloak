1) Keycloack: É um servidor opensource de autenticação.
2) um simples docker-compose com um serviço pro mysql e pro keycloak resolve a situação. Eles ficam rodando em localhost 
na porta 8080. Basta executar um docker-compose up no mesmo diretório
      version: '3.8'

      services:
      db:
         container_name: db_mysql
         image: mysql:latest
         environment:
            - MYSQL_ROOT_PASSWORD=root_pwd
            - MYSQL_USER=admin
            - MYSQL_PASSWORD=pa55word
         ports:
            - "3306:3306"
         volumes:
            - .docker/db/mysql:/var/lib/mysql
      keycloak:
         container_name: keycloak
         image: quay.io/keycloak/keycloak:latest
         environment:
            - KEYCLOAK_ADMIN=admin
            - KEYCLOAK_ADMIN_PASSWORD=admin
            - KC_DB=mysql
            - KC_DB_USERNAME=admin
            - KC_DB_PASSWORD=pa55word
            - KC_DB_URL_HOST=db
            - KC_DB_URL_PORT=3306
            - KC_DB_SCHEMA=keycloak
         ports:
            - 8080:8080
         command: start-dev
         depends_on:
            - db
         
4) Criando um Realm: Um realm é um domínio de autenticação onde residem múltiplos usuários com seus respectivos
níveis de permissão. Usuários logam e residem em um realm, e realms são isolados. Para criar um realm no keycloak,
basta ir no menu dropdown, e criar um realm dando um nome ao mesmo. Aplicações também estão restritas a um realm,
com cada realm tendo o seu conjunto de aplicações.

5) Criando um cliente no keycloak: Um cliente é uma aplicação que interage com o keycloak para consumir os seus serviços
(autenticação, por exemplo). Para criar um cliente, vá na aba de clients, forneça uma id e uma url de redirecionamento. No
exemplo, eu criei uma url para um cliente "java_client" em localhost:8085

6) Criando roles: roles são níveis de permissão. É possível criar uma role a nível de realm e a nível de cliente. Eu criei
um role a nível de cliente selecionando o "java_client" e criando um role "test_role".

7) Criando um usuário: entidade individual que pode consumir os serviços do seu servidor keycloak dentro de um realm em par-
ticular. Basta ir na aba clients para criar um cliente. Não esquecer de fornecer um credencial para o cliente na aba 
"Credentials".

8) Gerando um token de autenticação.
   Basta consumir do endpoint http://localhost:8080/seuRealm/master/protocol/openid-connect/token
   manda-se no POST a id de um cliente e de um usuário cadastrado. Esse repositório tem um cliente
   java implementado que faz isso.
