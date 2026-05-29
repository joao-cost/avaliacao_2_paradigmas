# Explicação da Interface Gráfica (MainGUI.java)

Este arquivo explica em detalhe como funciona a interface gráfica do projeto Zoológico, feita 100% em Java puro usando a biblioteca **Swing** (que já vem dentro do JDK, sem dependências externas).

A lógica de negócio (`Animal.java`, `Zoologico.java`, `Tratador.java` e subclasses) **não foi alterada** para criar a GUI — só substituímos a camada de apresentação. Isso é uma demonstração concreta de **separação de responsabilidades**.

---

## Visão geral

| Conceito | Onde aparece | Para que serve |
|---|---|---|
| `JFrame` | Janela principal | A "moldura" do programa |
| `JPanel` | Container invisível | Agrupar componentes |
| `JLabel` | Rótulo de texto | Mostrar textos fixos |
| `JButton` | Botão clicável | Acionar uma operação |
| `JTextField` | Caixa de texto | Receber texto digitado |
| `JComboBox` | Dropdown | Escolher de uma lista |
| `JCheckBox` | Caixa marcável | Sim/não, ligado/desligado |
| `JTable` | Tabela | Mostrar listas tabulares |
| `JOptionPane` | Pop-ups prontos | Avisos, perguntas, formulários |
| `JScrollPane` | Wrapper com scroll | Adicionar barra de rolagem |
| `BorderLayout` | Layout em 5 regiões | Topo, baixo, centro etc. |
| `GridLayout` | Layout em grade | Distribuir em linhas e colunas |
| `ActionListener` (lambda) | Tratador de evento | Reagir ao clique do botão |

---

## 1) O método `main` e a thread do Swing

```java
public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            criarJanelaPrincipal();
        }
    });
}
```

Java é multi-thread. O `main()` roda na "thread principal", mas o Swing exige que toda manipulação de componentes gráficos aconteça numa thread especial chamada **EDT** (Event Dispatch Thread).

`SwingUtilities.invokeLater(...)` agenda o código pra rodar na EDT, garantindo segurança de thread.

O `new Runnable() { public void run() { ... } }` é uma **classe anônima** — uma classe sem nome criada na hora, que só serve para "embrulhar" o código a ser executado.

---

## 2) Criando a janela principal

```java
static void criarJanelaPrincipal() {
    janelaPrincipal = new JFrame("Zoológico - Sistema de Cadastro");
    janelaPrincipal.setSize(500, 450);
    janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    janelaPrincipal.setLayout(new BorderLayout());
```

| Linha | Função |
|---|---|
| `new JFrame("...")` | Cria a janela vazia com o título dado |
| `setSize(500, 450)` | Largura e altura em pixels |
| `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)` | Ao clicar no X, encerra o programa de verdade |
| `setLayout(new BorderLayout())` | Define como os componentes serão organizados |

### O `BorderLayout`

Divide a janela em 5 regiões:

```
+------------------------------+
|           NORTH              |
+------+--------------+--------+
| WEST |    CENTER    |  EAST  |
+------+--------------+--------+
|           SOUTH              |
+------------------------------+
```

No nosso programa, usamos só **NORTH** (título) e **CENTER** (botões).

---

## 3) O título no topo

```java
JLabel titulo = new JLabel("=== ZOOLÓGICO ===", SwingConstants.CENTER);
titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
janelaPrincipal.add(titulo, BorderLayout.NORTH);
```

- `JLabel` é um componente de texto não editável.
- `SwingConstants.CENTER` centraliza o texto dentro do label.
- `setFont(new Font("SansSerif", Font.BOLD, 20))` aplica fonte SansSerif, negrito, tamanho 20.
- `createEmptyBorder(15, 0, 10, 0)` cria margem invisível (top, left, bottom, right).
- `add(titulo, BorderLayout.NORTH)` cola o label no topo da janela.

---

## 4) O grid de botões

```java
JPanel painelBotoes = new JPanel(new GridLayout(5, 2, 10, 10));
painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
```

- `JPanel` é um container invisível, usado pra organizar componentes.
- `GridLayout(5, 2, 10, 10)`: 5 linhas, 2 colunas, espaçamento horizontal/vertical de 10px.

Distribuição visual:

```
[ Cadastrar Animal  ] [ Listar Animais    ]
[ Atualizar Animal  ] [ Remover Animal    ]
[ Cadastrar Tratador] [ Listar Tratadores ]
[ Atualizar Tratador] [ Remover Tratador  ]
[ Atribuir Tratador ] [ Sair              ]
```

### Adicionando os botões

```java
adicionarBotao(painelBotoes, "Cadastrar Animal", e -> cadastrarAnimal());
adicionarBotao(painelBotoes, "Listar Animais", e -> listarAnimais());
// ...
```

A sintaxe `e -> cadastrarAnimal()` é uma **expressão lambda** — uma forma curta de criar um `ActionListener`. Significa:

> "Quando o evento de clique `e` acontecer, execute `cadastrarAnimal()`."

A versão "longa" equivalente seria:

```java
new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        cadastrarAnimal();
    }
}
```

### O helper `adicionarBotao`

```java
static void adicionarBotao(JPanel painel, String texto, ActionListener acao) {
    JButton botao = new JButton(texto);
    botao.addActionListener(acao);
    painel.add(botao);
}
```

Evita repetir as três linhas (criar botão, adicionar listener, adicionar ao painel) dez vezes. Princípio **DRY** (Don't Repeat Yourself).

---

## 5) Exibir a janela

```java
janelaPrincipal.setLocationRelativeTo(null);
janelaPrincipal.setVisible(true);
```

- `setLocationRelativeTo(null)` centraliza a janela na tela.
- `setVisible(true)` torna a janela visível — sempre a última coisa, depois de configurar tudo. JFrames nascem invisíveis por padrão.

---

## 6) `cadastrarAnimal()` — formulário em pop-up

### Criar os componentes

```java
String[] tipos = {"Leão", "Elefante", "Macaco", "Cobra"};
JComboBox<String> cbTipo = new JComboBox<>(tipos);
JTextField tfNome = new JTextField(15);
JTextField tfIdade = new JTextField(5);
JCheckBox cbPec = new JCheckBox("Peçonhenta (somente para Cobra)");
```

- `JComboBox<String>` é o dropdown. O `<String>` é generics: lista strings.
- `JTextField(15)` é uma caixa de texto. O `15` é dica de largura.
- `JCheckBox` é uma caixa marcável.

### Montar o painel do formulário

```java
JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
painel.add(new JLabel("Tipo:"));
painel.add(cbTipo);
painel.add(new JLabel("Nome:"));
painel.add(tfNome);
painel.add(new JLabel("Idade:"));
painel.add(tfIdade);
painel.add(new JLabel(""));
painel.add(cbPec);
```

`GridLayout(0, 2, 5, 5)`: o `0` significa "número de linhas automático" (cresce conforme adiciona). Resultado:

```
+---------+--------------------+
| Tipo:   | [Leão        v]    |
| Nome:   | [_____________]    |
| Idade:  | [____]             |
|         | [ ] Peçonhenta...  |
+---------+--------------------+
```

### Exibir o formulário e capturar resposta

```java
int resultado = JOptionPane.showConfirmDialog(janelaPrincipal, painel,
        "Cadastrar Animal", JOptionPane.OK_CANCEL_OPTION);

if (resultado != JOptionPane.OK_OPTION) {
    return;
}
```

`JOptionPane.showConfirmDialog`:
1. Trava a janela principal (modal).
2. Mostra o pop-up com o painel.
3. Espera o usuário clicar OK ou Cancelar.
4. Retorna `int` indicando qual botão foi clicado.

### Validar dados

```java
String nome = tfNome.getText().trim();
String idadeStr = tfIdade.getText().trim();

if (nome.isEmpty() || idadeStr.isEmpty()) {
    mostrarErro("Preencha o nome e a idade.");
    return;
}

int idade;
try {
    idade = Integer.parseInt(idadeStr);
} catch (NumberFormatException ex) {
    mostrarErro("Idade deve ser um número inteiro.");
    return;
}
```

- `tfNome.getText()` pega o texto digitado.
- `Integer.parseInt(...)` tenta converter string em número. Se falhar, capturamos `NumberFormatException`.

### Criar o objeto com polimorfismo

```java
int tipo = cbTipo.getSelectedIndex();
int id = zoo.gerarIdAnimal();

Animal a;
switch (tipo) {
    case 0: a = new Leao(id, nome, idade); break;
    case 1: a = new Elefante(id, nome, idade); break;
    case 2: a = new Macaco(id, nome, idade); break;
    case 3: a = new Cobra(id, nome, idade, cbPec.isSelected()); break;
    default: return;
}

zoo.cadastrarAnimal(a);
mostrarInfo("Animal cadastrado com ID " + id + ".");
```

- `getSelectedIndex()` retorna o índice do item selecionado (0=Leão, 1=Elefante etc.).
- `cbPec.isSelected()` retorna `true`/`false`.
- O `switch` cria a subclasse correta — **mesma lógica do Main.java console**, só o input vem de outro lugar.

---

## 7) `listarAnimais()` — tabela com `JTable`

### Modelo de dados

```java
String[] colunas = {"ID", "Espécie", "Nome", "Idade", "Tratador", "Detalhes"};
DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
```

`JTable` segue o padrão **MVC**:
- **Model** (`DefaultTableModel`): guarda os dados.
- **View** (`JTable`): mostra na tela o que está no modelo.

Aqui criamos o modelo com nomes de coluna e `0` linhas iniciais.

### Preencher linhas

```java
for (Animal a : zoo.animais) {
    String tratador = (a.tratador != null) ? a.tratador.nome : "-";
    String detalhes = "";
    if (a instanceof Cobra) {
        detalhes = ((Cobra) a).peconhenta ? "Peçonhenta" : "Não peçonhenta";
    }
    modelo.addRow(new Object[]{a.id, a.especie, a.nome, a.idade, tratador, detalhes});
}
```

- Operador ternário `condicao ? valorSeTrue : valorSeFalse` evita `if/else` longos.
- `instanceof Cobra` + cast pra acessar `peconhenta` (atributo exclusivo da Cobra).
- `addRow(new Object[]{...})` adiciona uma linha com valores de tipos misturados.

### Mostrar a tabela

```java
JTable tabela = new JTable(modelo);
tabela.setEnabled(false);
JScrollPane scroll = new JScrollPane(tabela);
scroll.setPreferredSize(new Dimension(600, 250));

JOptionPane.showMessageDialog(janelaPrincipal, scroll, "Lista de Animais",
        JOptionPane.PLAIN_MESSAGE);
```

- `new JTable(modelo)` cria a view amarrada ao modelo.
- `setEnabled(false)` impede edição (estamos só listando).
- `JScrollPane` adiciona barra de rolagem automática.
- `setPreferredSize(...)` define dimensões em pixels.
- `JOptionPane.showMessageDialog(...)` exibe num pop-up simples.

---

## 8) `atribuirTratador()` — dois dropdowns relacionando objetos

```java
JComboBox<Animal> cbAnimal = new JComboBox<>(zoo.animais.toArray(new Animal[0]));
JComboBox<Tratador> cbTratador = new JComboBox<>(zoo.tratadores.toArray(new Tratador[0]));
```

- `toArray(new Animal[0])` converte `ArrayList<Animal>` em `Animal[]`.
- O dropdown lista os objetos diretamente.

### Por que `toString()` foi adicionado em `Animal` e `Tratador`

```java
// Em Animal.java
public String toString() {
    return "ID " + id + " - " + nome + " (" + especie + ")";
}
```

Quando o `JComboBox` precisa exibir um item, chama `toString()` daquele objeto. Sem essa sobrescrita, apareceria algo feio tipo `Leao@7a81197d`. Com ela, aparece "ID 1 - Simba (Leão)".

### Selecionar e ligar

```java
Animal a = (Animal) cbAnimal.getSelectedItem();
Tratador t = (Tratador) cbTratador.getSelectedItem();
a.tratador = t;
```

- `getSelectedItem()` retorna `Object`, então fazemos cast.
- `a.tratador = t` materializa a **associação**. O animal passa a apontar pro tratador.
- Como Java passa objetos por referência, isso reflete na lista do Zoologico — é o mesmo objeto.

---

## 9) Helpers globais

### Selecionar animal e tratador

```java
static Animal selecionarAnimal(String mensagem) {
    Animal[] arr = zoo.animais.toArray(new Animal[0]);
    return (Animal) JOptionPane.showInputDialog(janelaPrincipal, mensagem,
            "Selecionar Animal", JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
}
```

`JOptionPane.showInputDialog` com array → mostra um dropdown ao usuário. Retorna o objeto selecionado (ou `null` se cancelar).

### Mensagens de info/erro

```java
static void mostrarInfo(String msg) {
    JOptionPane.showMessageDialog(janelaPrincipal, msg, "Informação",
            JOptionPane.INFORMATION_MESSAGE);
}

static void mostrarErro(String msg) {
    JOptionPane.showMessageDialog(janelaPrincipal, msg, "Erro",
            JOptionPane.ERROR_MESSAGE);
}
```

Encapsulam pop-ups simples, evitando repetição. A constante `INFORMATION_MESSAGE` mostra ícone azul, `ERROR_MESSAGE` mostra ícone vermelho.

---

## Estrutura visual completa

```
JFrame (janela principal)
+-- BorderLayout
    +-- NORTH: JLabel "=== ZOOLÓGICO ==="
    +-- CENTER: JPanel
                 +-- GridLayout (5 linhas x 2 colunas)
                 +-- JButton "Cadastrar Animal"   ----+
                 +-- JButton "Listar Animais"         |
                 +-- JButton "Atualizar Animal"       |
                 +-- JButton "Remover Animal"         |
                 +-- JButton "Cadastrar Tratador"    -+- cada um com seu
                 +-- JButton "Listar Tratadores"      |   ActionListener (lambda)
                 +-- JButton "Atualizar Tratador"     |
                 +-- JButton "Remover Tratador"       |
                 +-- JButton "Atribuir Tratador"      |
                 +-- JButton "Sair"               ----+
```

---

## Fluxo de execução

```
1. usuário roda "java MainGUI"
       v
2. main() executa
       v
3. SwingUtilities.invokeLater(...) agenda a criação da janela na EDT
       v
4. criarJanelaPrincipal() roda:
       a. cria o JFrame
       b. cria o JLabel do título e cola no NORTH
       c. cria o JPanel com GridLayout 5x2
       d. cria 10 JButtons, cada um com ActionListener (lambda)
       e. cola o painel no CENTER
       f. centraliza a janela
       g. torna visível
       v
5. Programa fica vivo, aguardando eventos.
       v
6. Usuário clica num botão -> ActionListener -> método correspondente (ex: cadastrarAnimal)
       v
7. Método abre pop-up (JOptionPane), conversa com usuário e chama o zoo (Zoologico)
       v
8. Volta pro passo 5 até o usuário clicar em "Sair" ou no X.
```

---

## Conceitos para defender na apresentação

### Programação orientada a eventos

No `Main.java` (console), o programa **pergunta ativamente** ao usuário ("qual sua escolha?", "qual o nome?"). Fluxo linear de cima pra baixo.

Na `MainGUI.java`, o programa fica **parado, esperando**. Ele só reage quando o usuário faz algo. Cada interação dispara um **evento**, e cada evento tem um **listener** que decide o que fazer.

Esse é o modelo usado em **toda interface gráfica moderna** — web, mobile, desktop. O `ActionListener` é o equivalente direto do `onclick` do JavaScript, do `onPressed` do Flutter.

### Separação de responsabilidades

A lógica de negócio (`Animal`, `Zoologico`, etc.) **não foi alterada** ao criar a GUI. Só substituímos a camada de apresentação:

- `Main.java` usa `Scanner` e `System.out` (console).
- `MainGUI.java` usa `JFrame` e `JOptionPane` (gráfica).
- **Ambos chamam os mesmos métodos do mesmo objeto `Zoologico`.**

Isso é a separação **Model-View-Controller** (MVC) na prática.

### Polimorfismo continua presente

Quando `listarAnimais()` percorre `zoo.animais` e cada item é tratado como `Animal`, o `instanceof Cobra` permite descobrir o tipo real e acessar atributos específicos. Mesmo polimorfismo da versão console — só consumido de outra forma na exibição.

---

## Como rodar as duas versões

```bash
cd zoologico
javac *.java

java Main      # versão console (Scanner)
java MainGUI   # versão gráfica (Swing)
```
