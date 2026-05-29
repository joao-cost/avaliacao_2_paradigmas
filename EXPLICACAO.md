# Explicação do Projeto Zoológico

Projeto desenvolvido em Java seguindo o paradigma de Orientação a Objetos, com CRUD em memória (sem persistência em banco de dados ou arquivos).

---

## Estrutura geral

| Arquivo | Papel | Categoria |
|---|---|---|
| `Animal.java` | Classe base com atributos e comportamentos comuns | **Model** (dados) |
| `Leao.java`, `Elefante.java`, `Macaco.java`, `Cobra.java` | Subclasses específicas (herdam de Animal) | **Model** (dados) |
| `Tratador.java` | Classe associada a Animal | **Model** (dados) |
| `Zoologico.java` | Gerencia as listas e contém as regras de negócio (CRUD) | **Controller** (lógica) |
| `Main.java` | Menu interativo via Scanner | **View** (interface) |

Essa separação corresponde a uma versão simplificada do padrão **MVC** (Model-View-Controller).

---

## Conceitos de POO aplicados

| Conceito | Onde aparece | Exemplo no código |
|---|---|---|
| **Classe e objeto** | Toda a estrutura | `new Leao(1, "Simba", 7)` cria um objeto da classe Leao |
| **Encapsulamento** | Atributos ficam dentro das classes | `nome`, `idade` dentro de Animal |
| **Herança** | Subclasses estendem Animal | `class Leao extends Animal` |
| **Polimorfismo** | Mesma chamada com comportamentos diferentes | `a.imprimir()` executa a versão da subclasse correta |
| **Sobrescrita (override)** | Subclasses redefinem `imprimir()` | Cada animal imprime de um jeito |
| **Reuso com `super`** | Subclasse chama código da pai | `super(id, nome, "Leão", idade)` |
| **Associação** | Animal aponta para um Tratador | `Animal.tratador` |
| **Agregação** | Zoológico contém listas de objetos | `ArrayList<Animal> animais` |

---

# 1) Animal.java — A classe base

## Atributos

```java
class Animal {
    int id;
    String nome;
    String especie;
    int idade;
    Tratador tratador;
```

Esses são os **atributos comuns** a todo animal do zoológico. Independente da espécie, todo animal tem ID, nome, espécie, idade e (opcionalmente) um tratador.

A classe Animal funciona como um **molde genérico**. Ela define o que todo animal precisa ter — e as subclasses (Leão, Cobra etc.) adicionam o que é específico de cada espécie.

## Construtor

```java
Animal(int id, String nome, String especie, int idade) {
    this.id = id;
    this.nome = nome;
    this.especie = especie;
    this.idade = idade;
}
```

- `this` se refere ao objeto atual sendo construído.
- Como o parâmetro `id` tem o mesmo nome do atributo `id`, usamos `this.id = id` para diferenciar: "este atributo do objeto recebe o valor do parâmetro".
- O atributo `tratador` **não está no construtor** — ele começa `null` (vazio) e é preenchido depois, via opção do menu.

## Métodos comuns

```java
void comer() {
    System.out.println(nome + " está comendo");
}

void dormir() {
    System.out.println(nome + " está dormindo");
}
```

Comportamentos que **todo animal faz**. Por isso ficam na classe pai — vão ser herdados automaticamente pelas subclasses, sem precisar repetir o código.

## O método `imprimir()`

```java
void imprimir() {
    System.out.println("ID: " + id);
    System.out.println("Nome: " + nome);
    System.out.println("Espécie: " + especie);
    System.out.println("Idade: " + idade + " anos");
    if (tratador != null) {
        System.out.println("Tratador: " + tratador.nome + " (matrícula " + tratador.matricula + ")");
    } else {
        System.out.println("Tratador: nenhum");
    }
}
```

Imprime os dados básicos. O `if (tratador != null)` evita `NullPointerException` quando o animal ainda não tem tratador atribuído.

---

# 2) Subclasses — Herança em ação

## Leao.java (modelo simples)

```java
class Leao extends Animal {

    Leao(int id, String nome, int idade) {
        super(id, nome, "Leão", idade);
    }

    void rugir() {
        System.out.println(nome + " está rugindo");
    }

    void imprimir() {
        System.out.println("=== Leão ===");
        super.imprimir();
        comer();
        dormir();
        rugir();
        System.out.println();
    }
}
```

### `extends Animal`

Significa que **Leão É-UM Animal**. Herda automaticamente:
- Atributos: `id`, `nome`, `especie`, `idade`, `tratador`
- Métodos: `comer()`, `dormir()`, `imprimir()`

Não precisa redeclarar nada — vem de graça.

### `super(...)` no construtor

```java
super(id, nome, "Leão", idade);
```

`super` significa "chama o construtor da classe pai".

- O construtor de `Leao` recebe 3 parâmetros (id, nome, idade).
- O de `Animal` precisa de 4 (id, nome, **especie**, idade).
- Como todo Leão tem espécie fixa `"Leão"`, já passamos pronta. O usuário não precisa digitar.

**Regra do Java**: se for usar `super(...)`, tem que ser a **primeira linha** do construtor.

### Método específico

```java
void rugir() { ... }
```

Só existe em Leão. Animal não ruge, Cobra não ruge. Por isso fica na subclasse.

### Sobrescrita do `imprimir()`

A subclasse redefine um método que já existia na classe pai (chamado de **override**):

1. Imprime cabeçalho `=== Leão ===`
2. Chama `super.imprimir()` para reutilizar a impressão dos dados básicos
3. Executa `comer()`, `dormir()` (herdados) e `rugir()` (próprio)

Quando você faz `Animal a = new Leao(...)` e depois `a.imprimir()`, o Java executa **esta versão (do Leão)**, não a do Animal. Isso é **polimorfismo**.

## Cobra.java (subclasse com atributo extra)

```java
class Cobra extends Animal {

    boolean peconhenta;

    Cobra(int id, String nome, int idade, boolean peconhenta) {
        super(id, nome, "Cobra", idade);
        this.peconhenta = peconhenta;
    }

    void rastejar() {
        System.out.println(nome + " está rastejando");
    }

    void imprimir() {
        System.out.println("=== Cobra ===");
        super.imprimir();
        if (peconhenta) {
            System.out.println("Peçonhenta: sim");
        } else {
            System.out.println("Peçonhenta: não");
        }
        comer();
        dormir();
        rastejar();
        System.out.println();
    }
}
```

A Cobra mostra que **subclasses podem ter atributos próprios** além dos herdados.

- `boolean peconhenta;` → só Cobra tem.
- No construtor: primeiro `super(...)` para montar a parte "Animal", depois `this.peconhenta = peconhenta`.
- No `imprimir()`, depois do `super.imprimir()`, imprime também o atributo extra.

## Árvore de herança

```
                  Animal
       (id, nome, especie, idade,
        tratador, comer, dormir,
              imprimir)
                    |
        +-----------+-----------+--------------+
        |           |           |              |
       Leão     Elefante     Macaco          Cobra
        |           |           |              |
      rugir   tomarBanho     pular     peconhenta + rastejar
```

---

# 3) Zoologico.java — O cérebro do CRUD

## Atributos

```java
import java.util.ArrayList;

class Zoologico {
    ArrayList<Animal> animais;
    ArrayList<Tratador> tratadores;
    int proxIdAnimal;
    int proxMatricula;
```

- `ArrayList<Animal>` → lista dinâmica (cresce conforme adiciona) que **só aceita objetos do tipo Animal** ou subclasses. O `<Animal>` é chamado de **generics**.
- `proxIdAnimal` e `proxMatricula` → contadores que geram IDs únicos sequenciais (1, 2, 3...).

## Construtor

```java
Zoologico() {
    this.animais = new ArrayList<>();
    this.tratadores = new ArrayList<>();
    this.proxIdAnimal = 1;
    this.proxMatricula = 1;
}
```

Roda **uma vez**, quando você faz `new Zoologico()`. Inicializa as listas vazias e os contadores em 1.

**Importante**: sempre inicialize coleções no construtor. Se ficar `null`, qualquer `animais.add(...)` daria `NullPointerException`.

## Geração de IDs

```java
int gerarIdAnimal() {
    return proxIdAnimal++;
}
```

O `++` depois da variável **retorna o valor atual e depois incrementa**.
- Primeira chamada: retorna 1, deixa contador em 2.
- Segunda chamada: retorna 2, deixa contador em 3.
- E assim por diante.

Cada chamada gera um ID único, sem repetir — mesmo se você remover um animal e cadastrar outro depois.

## Cadastrar (Create)

```java
void cadastrarAnimal(Animal a) {
    animais.add(a);
}
```

`animais.add(a)` é um método pronto do `ArrayList` que joga o objeto no final da lista.

## Buscar (Read pontual)

```java
Animal buscarAnimalPorId(int id) {
    for (Animal a : animais) {
        if (a.id == id) {
            return a;
        }
    }
    return null;
}
```

- `for (Animal a : animais)` é o **for-each**: percorre cada animal da lista.
- Se achar, retorna imediatamente.
- Se o loop terminar sem achar, retorna `null` (convenção em Java para "não encontrei").

Quem chama esse método deve verificar se o retorno é `null` antes de usar o objeto.

## Remover (Delete)

```java
boolean removerAnimal(int id) {
    Animal a = buscarAnimalPorId(id);
    if (a == null) {
        return false;
    }
    animais.remove(a);
    return true;
}
```

**Reusa** o `buscarAnimalPorId` em vez de duplicar a lógica de busca. Princípio importante: **não repetir código**.

Retorna `true` se removeu, `false` se não achou.

## Listar (Read em massa)

```java
void listarAnimais() {
    if (animais.isEmpty()) {
        System.out.println("Nenhum animal cadastrado.");
        return;
    }
    for (Animal a : animais) {
        a.imprimir();
    }
}
```

**Aqui acontece a mágica do polimorfismo**: mesmo a variável sendo do tipo `Animal`, ao chamar `a.imprimir()` o Java executa a versão da **subclasse de verdade** (Leão, Cobra etc.). Você nem precisa saber o tipo exato.

## CRUD de Tratador

Funciona exatamente igual ao de Animal, só que mais simples (Tratador só tem matrícula e nome).

## `removerTratador` — o caso especial

```java
boolean removerTratador(int matricula) {
    Tratador t = buscarTratadorPorMatricula(matricula);
    if (t == null) {
        return false;
    }
    // Desvincular o tratador dos animais que ele cuidava
    for (Animal a : animais) {
        if (a.tratador == t) {
            a.tratador = null;
        }
    }
    tratadores.remove(t);
    return true;
}
```

Antes de remover, percorre todos os animais e desvincula o tratador. Isso evita **referências penduradas** (animais apontando para um tratador que não existe mais na lista).

Em banco de dados isso se chama "chave estrangeira em cascata". Aqui é a versão manual.

O `==` (`a.tratador == t`) compara se são **o mesmo objeto na memória**, não se têm o mesmo nome. Funciona porque os animais guardam a referência exata.

---

# 4) Main.java — A interface com o usuário

## Atributos estáticos

```java
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Zoologico zoo = new Zoologico();
```

- `Scanner sc` → "teclado" do programa (lê o que o usuário digita).
- `Zoologico zoo` → "memória" do programa (instância única que guarda tudo).
- `static` significa "pertence à classe, não a um objeto específico". Permite usar `sc` e `zoo` direto em todos os métodos da `Main`.

## Loop principal

```java
public static void main(String[] args) {
    int opcao;
    do {
        mostrarMenu();
        opcao = lerInt("Escolha: ");
        System.out.println();

        switch (opcao) {
            case 1: cadastrarAnimal(); break;
            case 2: zoo.listarAnimais(); break;
            // ...
            case 0: System.out.println("Encerrando..."); break;
            default: System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}
```

- `do { ... } while (opcao != 0);` → loop que repete até o usuário digitar 0.
- Usamos `do-while` (não `while`) porque queremos mostrar o menu **pelo menos uma vez** antes de testar a condição.
- O `switch` decide qual método chamar.
- `break` é obrigatório no final de cada `case` em Java, senão ele continuaria executando os próximos (fall-through).

## Cadastrar Animal — polimorfismo em ação

```java
Animal a;
switch (tipo) {
    case 1: a = new Leao(id, nome, idade); break;
    case 2: a = new Elefante(id, nome, idade); break;
    case 3: a = new Macaco(id, nome, idade); break;
    case 4:
        boolean pec = lerSimNao("Peçonhenta? (s/n): ");
        a = new Cobra(id, nome, idade, pec);
        break;
    // ...
}
zoo.cadastrarAnimal(a);
```

Repara: a variável é declarada como `Animal a;` (classe pai), mas recebe `new Leao(...)` ou `new Cobra(...)` (subclasses). **Isso é polimorfismo na prática** — o "container" é genérico, o conteúdo é específico.

## Atualizar Animal — `instanceof` e cast

```java
a.nome = lerString("Novo nome: ");
a.idade = lerInt("Nova idade: ");
if (a instanceof Cobra) {
    ((Cobra) a).peconhenta = lerSimNao("Peçonhenta? (s/n): ");
}
```

- `instanceof Cobra` → verifica se o objeto é especificamente uma Cobra.
- `(Cobra) a` → **cast**: trata o objeto como Cobra para acessar o atributo `peconhenta`, que não existe na classe Animal.

Em Java, objetos são manipulados por **referência**. Quando você faz `a.nome = ...`, a alteração vale também na lista do Zoologico, porque é o mesmo objeto.

## Atribuir Tratador — a relação acontece aqui

```java
static void atribuirTratador() {
    int idAnimal = lerInt("ID do animal: ");
    Animal a = zoo.buscarAnimalPorId(idAnimal);
    if (a == null) { /* ... */ return; }

    int matricula = lerInt("Matrícula do tratador: ");
    Tratador t = zoo.buscarTratadorPorMatricula(matricula);
    if (t == null) { /* ... */ return; }

    a.tratador = t;
}
```

**Esse método materializa a relação Animal ↔ Tratador.**

A linha `a.tratador = t` guarda dentro do animal uma **referência** ao tratador. Não copia o tratador, só "aponta" pra ele. A partir daí, quando o animal for impresso, o `imprimir()` verifica `tratador != null` e mostra os dados.

## Helpers de leitura

```java
static int lerInt(String prompt) {
    System.out.print(prompt);
    while (!sc.hasNextInt()) {
        sc.next();
        System.out.print("Digite um número válido. " + prompt);
    }
    int n = sc.nextInt();
    sc.nextLine();
    return n;
}
```

- `sc.hasNextInt()` → verifica se o próximo valor é mesmo um número. Se não, descarta a entrada inválida e pede de novo.
- `sc.nextLine()` no final é **um truque importante**: `nextInt()` lê só o número e deixa o Enter (`\n`) no buffer. Se você chamar `nextLine()` depois para ler uma string, vai pegar essa quebra "fantasma" e achar que o usuário digitou vazio. O `sc.nextLine()` aqui "limpa" o buffer.

```java
static boolean lerSimNao(String prompt) {
    System.out.print(prompt);
    String resp = sc.nextLine().trim().toLowerCase();
    return resp.startsWith("s");
}
```

- `trim()` tira espaços nas pontas.
- `toLowerCase()` normaliza para minúsculo.
- Retorna `true` se começa com "s" (aceita "s", "sim", "Sim", "  S  " etc.).

---

# Diagrama de fluxo

```
        Usuário digita
              |
              v
    +-----------------+
    |     Main.java   |   <- conversa com o usuário
    |  (View)         |      (Scanner + System.out)
    +-----------------+
              |
              | delega
              v
    +-----------------+
    | Zoologico.java  |   <- aplica regras de negócio
    | (Controller)    |      (busca, remove, valida)
    +-----------------+
              |
              | manipula
              v
    +-----------------+
    | Animal, Cobra,  |   <- guardam os dados
    | Tratador        |
    | (Model)         |
    +-----------------+
```

A `Main` **não sabe** como os dados são armazenados — ela só pede pro `Zoologico` fazer.
O `Zoologico` **não sabe** quem está pedindo (poderia ser uma interface gráfica, uma API web, etc.).
Os objetos `Animal`/`Tratador` **não sabem** que estão num zoológico — são puramente dados.

Essa separação se chama **separação de responsabilidades**, e é um dos princípios mais importantes de qualquer projeto de software.

---

# Como compilar e rodar

```bash
cd zoologico
javac *.java
java Main
```

O `javac *.java` compila todos os arquivos `.java` da pasta, gerando arquivos `.class` (bytecode da JVM).
O `java Main` executa a classe que tem o método `main`.

---

# Resumo dos conceitos para a apresentação

1. **Classe**: molde, define estrutura e comportamento. Ex: `Animal`.
2. **Objeto**: instância de uma classe. Ex: `new Leao("Simba", 7)`.
3. **Atributo**: característica do objeto. Ex: `nome`, `idade`.
4. **Método**: comportamento do objeto. Ex: `comer()`, `rugir()`.
5. **Encapsulamento**: dados ficam dentro da classe.
6. **Herança** (`extends`): subclasse aproveita o que está na pai. Ex: `Leao extends Animal`.
7. **`super(...)`**: chama o construtor da pai. Garante que a parte herdada seja construída.
8. **`super.metodo()`**: chama a versão da pai do método (não a sobrescrita).
9. **Sobrescrita (override)**: subclasse redefine método da pai. Ex: cada animal tem seu `imprimir()`.
10. **Polimorfismo**: mesma chamada, comportamento diferente conforme o tipo real. `Animal a = new Cobra(...); a.imprimir();` chama o `imprimir()` da Cobra.
11. **Associação**: classe contém referência a outra. Ex: `Animal.tratador`.
12. **Agregação**: classe contém lista de outras. Ex: `Zoologico.animais`.
