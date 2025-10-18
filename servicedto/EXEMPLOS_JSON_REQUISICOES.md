# 📋 Exemplos de JSON para Requisições - Sistema de Biblioteca

## 🎯 Objetivo
Este arquivo contém exemplos práticos de JSON para testar os endpoints do Sistema de Biblioteca, seguindo exatamente os DTOs criados.

---

## 📦 DTOs e seus Exemplos JSON

### 1. LivroInserirDTO - Cadastro de Livro

#### Exemplo 1: Livro com um autor
```json
{
  "titulo": "O Senhor dos Anéis: A Sociedade do Anel",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```

#### Exemplo 2: Livro com múltiplos autores
```json
{
  "titulo": "Fundação e Império",
  "isbn": "9788535909555",
  "numeroPaginas": 320,
  "anoPublicacao": 1952,
  "status": "DISPONIVEL",
  "autorIds": [2, 3]
}
```

#### Exemplo 3: Livro emprestado
```json
{
  "titulo": "1984",
  "isbn": "9788535914849",
  "numeroPaginas": 416,
  "anoPublicacao": 1949,
  "status": "EMPRESTADO",
  "autorIds": [4]
}
```

#### Exemplo 4: Livro em manutenção
```json
{
  "titulo": "Dom Casmurro",
  "isbn": "9788535914849",
  "numeroPaginas": 256,
  "anoPublicacao": 1899,
  "status": "MANUTENCAO",
  "autorIds": [5]
}
```

#### Exemplo 5: Livro reservado
```json
{
  "titulo": "Cem Anos de Solidão",
  "isbn": "9788535914849",
  "numeroPaginas": 464,
  "anoPublicacao": 1967,
  "status": "RESERVADO",
  "autorIds": [6]
}
```

---

## 🔍 Exemplos de Requisições GET

### 1. Listar todos os livros (com paginação)
```
GET /livros?page=0&size=10&sort=titulo,asc
```

### 2. Buscar livros por autor
```
GET /livros/autor/1?page=0&size=5
```

### 3. Filtrar por status
```
GET /livros/status/DISPONIVEL?page=0&size=20
```

### 4. Filtrar por faixa de ano
```
GET /livros/ano?anoInicio=1950&anoFim=2000&page=0&size=15
```

### 5. Buscar livros disponíveis
```
GET /livros/disponiveis
```

### 6. Buscar livros mais antigos
```
GET /livros/antigos
```

### 7. Gerar relatório
```
GET /livros/relatorio
```

---

## 📊 Exemplos de Respostas JSON

### 1. Resposta do LivroDTO (após cadastro)
```json
{
  "id": 1,
  "titulo": "O Senhor dos Anéis: A Sociedade do Anel",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "dataCadastro": "2024-01-15T10:30:00",
  "nomesAutores": ["J.R.R. Tolkien"],
  "statusDescricao": "Disponível para empréstimo",
  "idadeLivro": 70
}
```

### 2. Resposta de listagem paginada
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "1984",
      "isbn": "9788535914849",
      "numeroPaginas": 416,
      "anoPublicacao": 1949,
      "status": "DISPONIVEL",
      "dataCadastro": "2024-01-15T10:30:00",
      "nomesAutores": ["George Orwell"],
      "statusDescricao": "Disponível para empréstimo",
      "idadeLivro": 75
    },
    {
      "id": 2,
      "titulo": "Dom Casmurro",
      "isbn": "9788535914849",
      "numeroPaginas": 256,
      "anoPublicacao": 1899,
      "status": "EMPRESTADO",
      "dataCadastro": "2024-01-15T11:00:00",
      "nomesAutores": ["Machado de Assis"],
      "statusDescricao": "Atualmente emprestado",
      "idadeLivro": 125
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false
  }
}
```

### 3. Resposta do relatório (LivroRelatorioDTO)
```json
[
  {
    "status": "DISPONIVEL",
    "totalLivros": 15,
    "anoMedio": 1975,
    "paginasMedias": 350,
    "livroMaisAntigo": "Dom Casmurro",
    "livroMaisNovo": "O Hobbit"
  },
  {
    "status": "EMPRESTADO",
    "totalLivros": 8,
    "anoMedio": 1980,
    "paginasMedias": 420,
    "livroMaisAntigo": "1984",
    "livroMaisNovo": "Fundação"
  },
  {
    "status": "MANUTENCAO",
    "totalLivros": 2,
    "anoMedio": 1950,
    "paginasMedias": 300,
    "livroMaisAntigo": "O Senhor dos Anéis",
    "livroMaisNovo": "O Senhor dos Anéis"
  },
  {
    "status": "RESERVADO",
    "totalLivros": 3,
    "anoMedio": 1960,
    "paginasMedias": 380,
    "livroMaisAntigo": "Cem Anos de Solidão",
    "livroMaisNovo": "Cem Anos de Solidão"
  }
]
```

---

## ❌ Exemplos de Erros de Validação

### 1. Título muito curto
```json
{
  "titulo": "A",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```
**Erro esperado:** `"Título deve ter entre 2 e 200 caracteres"`

### 2. ISBN inválido (menos de 13 dígitos)
```json
{
  "titulo": "O Senhor dos Anéis",
  "isbn": "978853361337",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```
**Erro esperado:** `"ISBN deve ter 13 dígitos"`

### 3. Ano inválido (muito antigo)
```json
{
  "titulo": "Livro Antigo",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 500,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```
**Erro esperado:** `"Ano deve ser maior que 1000"`

### 4. Número de páginas inválido
```json
{
  "titulo": "Livro Sem Páginas",
  "isbn": "9788533613379",
  "numeroPaginas": 0,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```
**Erro esperado:** `"Número de páginas deve ser maior que zero"`

### 5. Sem autores
```json
{
  "titulo": "Livro Sem Autor",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": "DISPONIVEL",
  "autorIds": []
}
```
**Erro esperado:** `"Pelo menos um autor é obrigatório"`

### 6. Status nulo
```json
{
  "titulo": "Livro Sem Status",
  "isbn": "9788533613379",
  "numeroPaginas": 576,
  "anoPublicacao": 1954,
  "status": null,
  "autorIds": [1]
}
```
**Erro esperado:** `"Status é obrigatório"`

---

## 🧪 Exemplos para Testes com Postman

### 1. Cadastrar livro básico
```http
POST http://localhost:8080/livros
Content-Type: application/json

{
  "titulo": "Harry Potter e a Pedra Filosofal",
  "isbn": "9788533613379",
  "numeroPaginas": 264,
  "anoPublicacao": 1997,
  "status": "DISPONIVEL",
  "autorIds": [1]
}
```

### 2. Listar livros com paginação
```http
GET http://localhost:8080/livros?page=0&size=5&sort=titulo,asc
```

### 3. Buscar por status
```http
GET http://localhost:8080/livros/status/DISPONIVEL?page=0&size=10
```

### 4. Filtrar por ano
```http
GET http://localhost:8080/livros/ano?anoInicio=1990&anoFim=2010&page=0&size=20
```

### 5. Buscar livros disponíveis
```http
GET http://localhost:8080/livros/disponiveis
```

### 6. Gerar relatório
```http
GET http://localhost:8080/livros/relatorio
```

---

## 📝 Dados de Teste Sugeridos

### Autores para cadastrar primeiro:
```json
[
  {
    "nome": "J.R.R. Tolkien",
    "nacionalidade": "Inglês",
    "dataNascimento": "1892-01-03",
    "biografia": "Escritor, poeta, filólogo e professor universitário britânico"
  },
  {
    "nome": "Isaac Asimov",
    "nacionalidade": "Americano",
    "dataNascimento": "1920-01-02",
    "biografia": "Escritor e bioquímico americano de origem russa"
  },
  {
    "nome": "George Orwell",
    "nacionalidade": "Inglês",
    "dataNascimento": "1903-06-25",
    "biografia": "Escritor, jornalista e ensaísta político inglês"
  },
  {
    "nome": "Machado de Assis",
    "nacionalidade": "Brasileiro",
    "dataNascimento": "1839-06-21",
    "biografia": "Escritor brasileiro, considerado o maior nome da literatura nacional"
  },
  {
    "nome": "Gabriel García Márquez",
    "nacionalidade": "Colombiano",
    "dataNascimento": "1927-03-06",
    "biografia": "Escritor, jornalista e ativista político colombiano"
  }
]
```

---

## 🎯 Dicas para Testes

1. **Cadastre os autores primeiro** antes de cadastrar os livros
2. **Use IDs válidos** nos `autorIds` (baseados nos autores cadastrados)
3. **Teste as validações** enviando dados inválidos
4. **Use diferentes status** para testar os filtros
5. **Teste a paginação** com diferentes valores de `page` e `size`
6. **Verifique as respostas** para entender a estrutura dos DTOs

---

**Estes exemplos cobrem todos os cenários de teste para o Sistema de Biblioteca!** 📚✨
