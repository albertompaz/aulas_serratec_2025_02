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

---

## 🗂️ Estrutura das Entidades

### 1. Entidade Autor

```java
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column
    private String nacionalidade;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(length = 1000)
    private String biografia;
    
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros = new HashSet<>();
    
    // construtores, getters e setters
}
```

### 2. Entidade Livro

```java
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;
    
    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLivro status;
    
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();
    
    // construtores, getters e setters
}

public enum StatusLivro {
    DISPONIVEL, EMPRESTADO, RESERVADO, MANUTENCAO
}
```

---

## 📦 DTOs para Implementar

### 1. DTO de Entrada (LivroInserirDTO)

```java
public class LivroInserirDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 2, max = 200, message = "Título deve ter entre 2 e 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "ISBN é obrigatório")
    @Pattern(regexp = "\\d{13}", message = "ISBN deve ter 13 dígitos")
    private String isbn;
    
    @Min(value = 1, message = "Número de páginas deve ser maior que zero")
    private Integer numeroPaginas;
    
    @Min(value = 1000, message = "Ano deve ser maior que 1000")
    @Max(value = 2024, message = "Ano não pode ser maior que o atual")
    private Integer anoPublicacao;
    
    @NotNull(message = "Status é obrigatório")
    private StatusLivro status;
    
    @NotEmpty(message = "Pelo menos um autor é obrigatório")
    private Set<Long> autorIds;
    
    // getters e setters
}
```

### 2. DTO de Saída (LivroDTO)

```java
public class LivroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer numeroPaginas;
    private Integer anoPublicacao;
    private StatusLivro status;
    private LocalDateTime dataCadastro;
    private List<String> nomesAutores;
    private String statusDescricao;
    private Integer idadeLivro; // anos desde publicação
    
    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.isbn = livro.getIsbn();
        this.numeroPaginas = livro.getNumeroPaginas();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.status = livro.getStatus();
        this.dataCadastro = livro.getDataCadastro();
        this.nomesAutores = livro.getAutores().stream()
            .map(Autor::getNome)
            .collect(Collectors.toList());
        this.statusDescricao = obterDescricaoStatus(livro.getStatus());
        this.idadeLivro = calcularIdadeLivro(livro.getAnoPublicacao());
    }
    
    private String obterDescricaoStatus(StatusLivro status) {
        return switch (status) {
            case DISPONIVEL -> "Disponível para empréstimo";
            case EMPRESTADO -> "Atualmente emprestado";
            case RESERVADO -> "Reservado por usuário";
            case MANUTENCAO -> "Em manutenção";
        };
    }
    
    private Integer calcularIdadeLivro(Integer anoPublicacao) {
        return LocalDate.now().getYear() - anoPublicacao;
    }
}
```

### 3. DTO para Relatórios (LivroRelatorioDTO)

```java
public interface LivroRelatorioDTO {
    String getStatus();
    Long getTotalLivros();
    Integer getAnoMedio();
    Integer getPaginasMedias();
    String getLivroMaisAntigo();
    String getLivroMaisNovo();
}
```

---

## 🎯 Endpoints para Implementar

### Controller de Livros

```java
@RestController
@RequestMapping("/livros")
public class LivroController {
    
    @Autowired
    private LivroService livroService;
    
    // POST /livros - Cadastrar livro
    @PostMapping
    public ResponseEntity<LivroDTO> inserir(@Valid @RequestBody LivroInserirDTO livroDTO) {
        // Implementar
    }
    
    // GET /livros - Listar todos com paginação
    @GetMapping
    public ResponseEntity<Page<LivroDTO>> listar(
        @PageableDefault(sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        // Implementar
    }
    
    // GET /livros/autor/{id} - Buscar por autor
    @GetMapping("/autor/{id}")
    public ResponseEntity<Page<LivroDTO>> buscarPorAutor(
        @PathVariable Long id, Pageable pageable) {
        // Implementar
    }
    
    // GET /livros/status/{status} - Filtrar por status
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<LivroDTO>> buscarPorStatus(
        @PathVariable StatusLivro status, Pageable pageable) {
        // Implementar
    }
    
    // GET /livros/ano - Filtrar por ano de publicação
    @GetMapping("/ano")
    public ResponseEntity<Page<LivroDTO>> buscarPorAno(
        @RequestParam Integer anoInicio,
        @RequestParam Integer anoFim,
        Pageable pageable) {
        // Implementar
    }
    
    // GET /livros/disponiveis - Livros disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<LivroDTO>> buscarDisponiveis() {
        // Implementar
    }
    
    // GET /livros/antigos - Livros mais antigos
    @GetMapping("/antigos")
    public ResponseEntity<List<LivroDTO>> buscarMaisAntigos() {
        // Implementar
    }
    
    // GET /livros/relatorio - Relatório por status
    @GetMapping("/relatorio")
    public ResponseEntity<List<LivroRelatorioDTO>> gerarRelatorio() {
        // Implementar
    }
}
```

---

## 🔍 Queries Customizadas para Treinar

### 1. Query Methods (Spring Data JPA)

```java
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    // Buscar por título contendo (case insensitive)
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    
    // Buscar por ISBN
    Optional<Livro> findByIsbn(String isbn);
    
    // Buscar por faixa de ano
    Page<Livro> findByAnoPublicacaoBetween(Integer anoInicio, Integer anoFim, Pageable pageable);
    
    // Buscar por status
    Page<Livro> findByStatus(StatusLivro status, Pageable pageable);
    
    // Buscar livros disponíveis
    List<Livro> findByStatus(StatusLivro status);
    
    // Buscar por autor
    Page<Livro> findByAutoresId(Long autorId, Pageable pageable);
    
    // Buscar por nome do autor
    List<Livro> findByAutoresNomeContainingIgnoreCase(String nomeAutor);
    
    // Contar livros por status
    Long countByStatus(StatusLivro status);
    
    // Verificar se ISBN existe
    boolean existsByIsbn(String isbn);
    
    // Buscar livros mais antigos
    List<Livro> findTop10ByOrderByAnoPublicacaoAsc();
    
    // Buscar livros mais recentes
    List<Livro> findTop10ByOrderByAnoPublicacaoDesc();
}
```

### 2. Queries JPQL

```java
// Buscar livros por múltiplos autores
@Query("SELECT DISTINCT l FROM Livro l JOIN l.autores a WHERE a.id IN :autorIds")
List<Livro> buscarPorAutores(@Param("autorIds") List<Long> autorIds);

// Buscar livros com mais de X páginas
@Query("SELECT l FROM Livro l WHERE l.numeroPaginas > :paginas")
List<Livro> buscarLivrosExtensos(@Param("paginas") Integer paginas);

// Buscar livros publicados em década específica
@Query("SELECT l FROM Livro l WHERE l.anoPublicacao BETWEEN :decadaInicio AND :decadaFim")
List<Livro> buscarPorDecada(@Param("decadaInicio") Integer inicio, 
                           @Param("decadaFim") Integer fim);

// Buscar livros com título ou ISBN
@Query("SELECT l FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) OR l.isbn = :termo")
Page<Livro> buscarPorTituloOuIsbn(@Param("termo") String termo, Pageable pageable);

// Buscar livros de autores de nacionalidade específica
@Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.nacionalidade = :nacionalidade")
List<Livro> buscarPorNacionalidadeAutor(@Param("nacionalidade") String nacionalidade);
```

### 3. Queries Nativas (SQL)

```java
// Relatório de livros por status
@Query(value = """
    SELECT 
        status,
        COUNT(*) as totalLivros,
        AVG(ano_publicacao) as anoMedio,
        AVG(numero_paginas) as paginasMedias,
        MIN(titulo) as livroMaisAntigo,
        MAX(titulo) as livroMaisNovo
    FROM livro 
    GROUP BY status 
    ORDER BY status
    """, nativeQuery = true)
List<LivroRelatorioDTO> gerarRelatorioPorStatus();

// Top 5 livros mais antigos
@Query(value = "SELECT * FROM livro ORDER BY ano_publicacao ASC LIMIT 5", nativeQuery = true)
List<Livro> buscarTop5MaisAntigos();

// Livros com mais autores
@Query(value = """
    SELECT l.*, COUNT(la.autor_id) as total_autores 
    FROM livro l 
    LEFT JOIN livro_autor la ON l.id = la.livro_id 
    GROUP BY l.id 
    HAVING total_autores > 1 
    ORDER BY total_autores DESC
    """, nativeQuery = true)
List<Livro> buscarLivrosComMaisAutores();

// Estatísticas por década
@Query(value = """
    SELECT 
        CONCAT(FLOOR(ano_publicacao/10)*10, 's') as decada,
        COUNT(*) as totalLivros,
        AVG(numero_paginas) as paginasMedias
    FROM livro 
    GROUP BY FLOOR(ano_publicacao/10) 
    ORDER BY decada
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
        // Validar ISBN único
        if (livroRepository.existsByIsbn(livroDTO.getIsbn())) {
            throw new IsbnDuplicadoException("ISBN já cadastrado");
        }
        
        // Buscar autores
        Set<Autor> autores = livroDTO.getAutorIds().stream()
            .map(autorService::buscar)
            .collect(Collectors.toSet());
        
        // Criar livro
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setNumeroPaginas(livroDTO.getNumeroPaginas());
        livro.setAnoPublicacao(livroDTO.getAnoPublicacao());
        livro.setStatus(livroDTO.getStatus());
        livro.setDataCadastro(LocalDateTime.now());
        livro.setAutores(autores);
        
        livro = livroRepository.save(livro);
        return new LivroDTO(livro);
    }
    
    public Page<LivroDTO> listar(Pageable pageable) {
        return livroRepository.findAll(pageable)
            .map(LivroDTO::new);
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
