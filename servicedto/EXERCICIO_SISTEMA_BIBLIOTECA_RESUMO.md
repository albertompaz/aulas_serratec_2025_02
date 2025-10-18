# 📚 Exercício Prático: Sistema de Biblioteca

## 🎯 Objetivo
Criar um sistema completo de gerenciamento de biblioteca com livros e autores, aplicando todos os conceitos estudados: DTOs, relacionamentos JPA, queries customizadas e arquitetura em camadas.

---

## 📋 Descrição do Problema

Você precisa implementar um sistema para uma biblioteca que gerencia **Livros** e **Autores**. Cada livro pode ter múltiplos autores (relacionamento Many-to-Many) e possui informações como título, ISBN, número de páginas, ano de publicação e status de disponibilidade.

### Requisitos Funcionais:
1. **Cadastrar livros** com validações
2. **Listar livros** com filtros e paginação
3. **Buscar livros por autor**
4. **Relatórios de biblioteca** (livros mais antigos, por gênero, etc.)
5. **Gerenciar autores**


## 📦 DTOs para Implementar

### 1. DTO de Entrada (LivroInserirDTO)

### 2. DTO de Saída (LivroDTO)

### 3. DTO para Relatórios (LivroRelatorioDTO)


---

## 🎯 Endpoints para Implementar

### Controller de Livros

---

## 🔍 Queries Customizadas para Treinar

### 1. Query Methods (Spring Data JPA)

```java
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    // Buscar por título contendo (case insensitive)
    Page<Livro> xxx(String titulo, Pageable pageable);
    
    // Buscar por ISBN
    Optional<Livro> xxx(String isbn);
    
    // Buscar por faixa de ano
    Page<Livro> xxx(Integer anoInicio, Integer anoFim, Pageable pageable);
    
    // Buscar por status
    Page<Livro> xxx(StatusLivro status, Pageable pageable);
    
    // Buscar livros disponíveis
    List<Livro> xxx(StatusLivro status);
    
    // Buscar por autor
    Page<Livro> xxx(Long autorId, Pageable pageable);
    
    // Buscar por nome do autor
    List<Livro> xxx(String nomeAutor);
    
    // Contar livros por status
    Long xxx(StatusLivro status);
    
    // Verificar se ISBN existe
    boolean xxx(String isbn);
    
    // Buscar livros mais antigos
    List<Livro> xxx();
    
    // Buscar livros mais recentes
    List<Livro> xxx();
}
```

### 2. Queries JPQL

```java
// Buscar livros por múltiplos autores
@Query("xxx")
List<Livro> buscarPorAutores(@Param("autorIds") List<Long> autorIds);

// Buscar livros com mais de X páginas
@Query("xxx")
List<Livro> buscarLivrosExtensos(@Param("paginas") Integer paginas);

// Buscar livros publicados em década específica
@Query("Sxxx")
List<Livro> buscarPorDecada(@Param("decadaInicio") Integer inicio, 
                           @Param("decadaFim") Integer fim);

// Buscar livros com título ou ISBN
@Query("xxx")
Page<Livro> buscarPorTituloOuIsbn(@Param("termo") String termo, Pageable pageable);

// Buscar livros de autores de nacionalidade específica
@Query("xxx")
List<Livro> buscarPorNacionalidadeAutor(@Param("nacionalidade") String nacionalidade);
```

### 3. Queries Nativas (SQL)

```java
// Relatório de livros por status
@Query(value = """
    xxx
    """, nativeQuery = true)
List<LivroRelatorioDTO> gerarRelatorioPorStatus();

// Top 5 livros mais antigos
@Query(value = "xxx", nativeQuery = true)
List<Livro> buscarTop5MaisAntigos();

// Livros com mais autores
@Query(value = """
    xxx
    """, nativeQuery = true)
List<Livro> buscarLivrosComMaisAutores();

// Estatísticas por década
@Query(value = """
    xxx
    """, nativeQuery = true)
List<Object[]> buscarEstatisticasPorDecada();
```

---

## 🏗️ Estrutura de Camadas

### Service Layer

```java
@Service
@Transactional
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AutorService autorService;
    
    public LivroDTO inserir(LivroInserirDTO livroDTO) {
        xxx
    }
    
    public Page<LivroDTO> listar(Pageable pageable) {
        xxx
    }
    
    // Outros métodos...
}
```

### Exception Handling

```java
// Exceções customizadas
public class IsbnDuplicadoException extends RuntimeException {
    public IsbnDuplicadoException(String message) {
        super(message);
    }
}

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String message) {
        super(message);
    }
}

public class LivroEmprestadoException extends RuntimeException {
    public LivroEmprestadoException(String message) {
        super(message);
    }
}
```

---

## 📝 Lista de Tarefas para os Alunos

### Fase 1: Estrutura Básica
- [ ] Criar enum `StatusLivro` (DISPONIVEL, EMPRESTADO, RESERVADO, MANUTENCAO)
- [ ] Criar entidades `Autor` e `Livro`
	Dados da entidade autor:
	- id
	- nome;
	- nacionalidade
	- dataNascimento
	- biografia
	- livros  (lista de livros)
	
	Dados da entidade livro:
	- id
	- titulo
	- isbn
	- numeroPaginas
	- anoPublicacao
	- status (StatusLivro)
	- dataCadastro
	- autores (lista de autores)
- [ ] Configurar relacionamento Many-to-Many
- [ ] Implementar DTOs de entrada e saída

### Fase 2: Endpoints Básicos
- [ ] Implementar cadastro de livros
- [ ] Implementar listagem com paginação
- [ ] Adicionar validações Bean Validation

### Fase 3: Queries e Filtros
- [ ] Implementar query methods
- [ ] Criar queries JPQL customizadas
- [ ] Implementar filtros por status e ano

### Fase 4: Relatórios
- [ ] Criar queries nativas para relatórios
- [ ] Implementar DTO de relatório
- [ ] Endpoint de relatório por status

### Fase 5: Validações e Tratamento de Erros
- [ ] Implementar exceções customizadas
- [ ] Adicionar validações de negócio (ISBN único)
- [ ] Tratamento global de exceções

---

## 🎯 Dicas de Implementação

1. **Use `@JoinTable`** para configurar a tabela intermediária do relacionamento Many-to-Many
2. **Implemente validação de ISBN único** no service
3. **Use `@Enumerated(EnumType.STRING)`** para persistir enums como string
4. **Crie métodos utilitários** para cálculos (idade do livro, descrição do status)
5. **Use `Set` para autores** para evitar duplicatas
6. **Implemente queries nativas** para relatórios complexos
7. **Teste os relacionamentos** criando livros com múltiplos autores

---

## 📚 Conceitos Aplicados

- ✅ **DTOs** (Data Transfer Objects)
- ✅ **Relacionamentos JPA** (Many-to-Many)
- ✅ **Enums** com JPA
- ✅ **Validações Bean Validation**
- ✅ **Query Methods** do Spring Data
- ✅ **Queries JPQL** customizadas
- ✅ **Queries Nativas** (SQL)
- ✅ **Paginação** com Pageable
- ✅ **Arquitetura em Camadas**
- ✅ **Tratamento de Exceções**
- ✅ **Conversão de Entidades para DTOs**

---

**Este exercício é perfeito para treinar relacionamentos Many-to-Many, enums, validações complexas e queries avançadas!** 📚✨
