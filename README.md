# Projeto Clima - UOL

Projeto Clima UOL escrito em Spring Boot

## Execução do Projeto via IDE

Após realizar o download do código, iremos importar o projeto para nossa IDE, no meu caso, Eclipse:

1. Abra o Eclipse
2. File -> Import -> Existing Maven projects -> Selecione nosso projeto
3. Dentro de "src/main/java" buscar o package com nome "br.com.uol.projetoclima" e abri-lo
4. Clicar com o botão direito na classe "ProjetoClimaApplication.java"
5. Run As -> Java Application

Isso fará com que a classe responsável por subir nosso servidor e iniciar nossa aplicação seja executada

Podemos verificar se a aplicação está no ar através do link gerado pelo Swagger, onde mostra uma documentação de nossa API:

[http://localhost:9030/swagger-ui.html#/](http://localhost:9030/swagger-ui.html#/)


## Execução de Testes

Para rodarmos nossos testes devemos:

1. Abrir uma interface de linha de comando
2. Navegarmos até a raiz do nosso projeto
3. Executarmos o comando maven


```bash
mvn test
```


## Empacotamento e execução 

Para empacotarmos nosso projeto, devemos:

1. Abrir uma interface de linha de comando
2. Navegarmos até a raiz do nosso projeto
3. Executarmos o comando
```bash
mvn clean install
```

Neste comando as dependências do projeto serão baixadas, os testes serão executados novamente para garantir um novo build sem erros e nosso projeto será empacotado

Após o final do comando, teremos um arquivo chamado "**projeto-clima-0.0.1-SNAPSHOT.jar**" dentro da nossa pasta "**target**"

Para rodarmos este novo .jar, executamos ainda dentro da raiz do projeto o comando 

```bash
java -jar target/projeto-clima-0.0.1-SNAPSHOT.jar
```

**Com isso teremos nosso projeto sendo executado**


## Ferramentas Utilizadas

* #### Spring Boot 
  Facilita as configurações necessárias do Spring

  Detecta automaticamente diversas configurações e dependencias inseridas em um projeto, como por exemplo o H2

  Não necessita de servidor para rodar pois já possui um Servlet Container


* #### Spring MVC

  Possibilita que nossa aplicação receba requisições HTTP

  Injeta dependências nos modulos para que nossa aplicação utilize o padrão MVC


* #### Spring Data

  Facilita a implementação de metodos que acessam ao Banco de Dados devido ao seu repository já pronto

  Mapeia as entidades para o Banco de Dados


* #### H2

  Banco de dados em memória, que é criado quando a aplicação é iniciada e destruido quando é parada, evitando assim a necessidade de configurar previamente um Banco de Dados convencional

  Facilita a execução de testes, evitando que eles falhem devido a algum dado já existente no Banco de Dados


* #### SpringTest e JUnit

  Construção de testes que são facilmente integrados à nossa aplicação Spring Boot


* #### Maven

  Responsável pelo build da aplicação

* #### Swagger

  Gera uma documentação para consumo da API de forma que qualquer alteração no código reflita na mesma

  Facilita testes de requisições
