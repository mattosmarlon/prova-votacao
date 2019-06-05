# prova-votacao

Conjunto de APIs REST para votações em pauta.

# Para executar a aplicação...

Para ver a aplicação rodando basta executar os seguintes passos:

1. Navegar até o diretório da aplicação para a execução dos seguintes comandos
2. Comando: gradle clean build
3. Comando: docker-compose up --build

Após esses passos a aplicação deve estar rodando na porta 8383, se disponível, e podemos conferir todos os endpoints através do Swagger.

http://localhost:8383/swagger-ui.html/

# Experiências e escolhas

O desenvolvimento foi feito com Spring Boot 2.1.5 possibilitando um 
desenvolvimento bastante agil e banco de dados escolhido foi o MySQL.

Durante o desenvolvimento houveram algumas escolhas que tiveram que ser tomadas, como 
qual ferramenta seria utilizada para executação de scripts de banco de dados, para 
esse quisito escolhi o liquibase por ser uma ferramenta familiar e de bom conteúdo na comunidade.

Uma das opções de executar via docker foi pela facilidade de enviar a aplicação para a 
nuvem, seja através de um pipeline via Jenkins ou outra ferramenta, entre outras 
opções que o docker possibilita. 

Dentre algumas melhorias que se poderia vir a desenvolver seria a remoção de algumas 
duplicações de código, indicados pelo Sonar. Além de mais testes em cima de classes de exceções e testes de carga para garantir uma certa estabilidade da aplicação. 


# Sonarqube
Para ter uma avaliação minima da aplicação utilizei o sonar para este objetivo, a 
ideia neste ponto era expor a integração com o mesmo, possíbilitando algumas dicas de 
refatoração que a ferramenta possíbilita. 

O resultado da avaliação pode ser visto em: https://sonarcloud.io/dashboard?id=marlon

## Postman

Além da execução via Swagger, podemos fazer testes via Postman, no diretório 
"/postman_requests/" existem arquivos para importação no postman onde será possível 
executar as APIs de criação de entidades, ou seja, criar pauta, sessão e executar uma votação.
Para isso será necessário importar os arquivos para dentro do postment, onde:   

- votacao.postman_environment.json
São os environments de localhost padrão. 

- votacao.postman_collection.json
Possui a coleção com os endpoints para executar os comandos POST.
