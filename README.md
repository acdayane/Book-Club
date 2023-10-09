# BookClub
## Curso Programação Web II - Back-end Java | Ada e BTG

## Requisitos do projeto:

- Um endpoint de cadastro, leitura, atualização e deleção (lógica ou física) de usuário que será usado no login da aplicação.
- Endpoints de buscas que recebem filtros opcionais e realizam consultas na camada de dados.
- Deve ser uma aplicação Spring Boot.
- Utilização do banco de dados Postgres.
- Linguagem Java 17.
- Deve ser inicializado a base de dados para utilização dos endpoints.
- Deve conter autenticação básica utilizando Spring Security.

## Sobre:

- A aplicação gerencia um clube de leitura, no qual o usuário pode se cadastrar, editar seus dados, excluir sua conta e buscar por outros usuários. As senhas são criptografadas e um token é necessário para acessar rotas protegidas.
- É possível cadastrar e deletar generos de livros e livros. É possível buscar todos os livros ou realizar buscas personalizadas por id ou fragmentos do título e do nome do autor. 
- Futuramente será possível criar uma reunião para debater acerca de um determinado livro, contendo livro, data, horário, link da videoconferencia e lista de participantes. O usuário poderá assinalar a reunião que irá participar e manter o registro das reuniões que frequentou.

### Como testar:
- User
```json
{
	"name":"Dayane",
	"email": "day@oi.com",
	"password": "1234"
}
```
- Book Genre
```json
{
	"name":"Romance",
}
```
- Book
```json
{
  "title": "150 Tons de Cinza",
  "author": "E L James",
  "genre_Id": 1
}
```
