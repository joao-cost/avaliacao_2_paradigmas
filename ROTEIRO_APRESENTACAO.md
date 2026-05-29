# Roteiro de apresentacao - Paradigmas Zoologico

## 1. Vitor Linsbinski - Introducao e estrutura OO
- Dizer que o trabalho e um micro sistema de zoologico em Java.
- Explicar que o objetivo foi aplicar Programacao Orientada a Objetos na pratica.
- Abrir `Animal.java` e mostrar que ela e a superclasse do sistema.
- Mostrar que `Leao.java`, `Elefante.java`, `Macaco.java` e `Cobra.java` herdam de `Animal`.
- Citar o uso de `super(...)` nos construtores das subclasses para reaproveitar atributos da classe pai.
- Mostrar `Animal.imprimir()` e explicar que ele imprime os dados basicos do objeto.
- Citar `toString()` em `Animal` e `Tratador` como apoio para a interface grafica.

Fala base:
- "Nosso sistema foi modelado com classes, onde `Animal` representa a superclasse e as outras especies sao especializacoes dela."
- "A heranca permite reaproveitar codigo, e o `super` liga a subclasse ao construtor da classe pai."

## 2. Fellipe Tomasella - Main, menu e CRUD no console
- Abrir `Main.java` e mostrar que ela e a primeira entrada do sistema.
- Explicar que aqui o sistema roda no console, sem interface grafica.
- Mostrar o `main()` com o `do/while` e o `switch(opcao)`.
- Explicar que cada opcao chama uma funcao: cadastrar, listar, atualizar ou remover.
- Mostrar os metodos `cadastrarAnimal()`, `atualizarAnimal()`, `removerAnimal()` e `atribuirTratador()`.
- Destacar os helpers `lerInt()`, `lerString()` e `lerSimNao()` como formas de validar entrada do usuario.
- Reforcar que a `Main.java` conversa com `Zoologico.java`, que guarda os dados em listas.

Fala base:
- "A `Main.java` controla o fluxo do programa no console, lendo a opcao do usuario e chamando o metodo correspondente."
- "Aqui aparecem laços, decisoes e funcoes, que sao a base do CRUD do sistema."

## 3. Joao Vitor - Paradigma imperativo/procedural x orientado a objetos
- Explicar que no paradigma imperativo/procedural o programa e construindo por uma sequencia de passos e funcoes.
- Dizer que nesse modelo o foco e a ordem das instrucoes: ler dados, decidir, executar e repetir.
- Mostrar que `Main.java` ajuda a entender isso, porque ela organiza o sistema em menu, escolhas e chamadas de funcoes.
- Explicar que no paradigma orientado a objetos o foco muda para classes e objetos que representam entidades reais.
- Mostrar que no sistema cada parte do zoologico virou um objeto: `Animal`, `Tratador` e `Zoologico`.
- Dizer que os objetos nao ficam so como dados soltos, mas tambem carregam comportamento, como `imprimir()`, `cadastrarAnimal()` e `removerTratador()`.
- Concluir que a diferenca principal e essa: no procedural o centro e o fluxo de comandos; no OO o centro sao os objetos e suas responsabilidades.

Fala base:
- "No paradigma procedural a gente pensa em passos e funcoes; no orientado a objetos a gente pensa em objetos com dados e comportamentos."
- "Nosso projeto mostra isso porque o zoologico foi transformado em classes que representam entidades reais do sistema."

## 4. Pabline - Interface grafica e comunicacao com o CRUD
- Abrir `MainGUI.java` e explicar que ela e a segunda entrada do sistema.
- Dizer que o projeto comecou sem interface grafica e rodava so no console, e depois ganhou essa versao visual como diferencial.
- Mostrar o `main()` da GUI com `SwingUtilities.invokeLater(...)`.
- Explicar que a janela principal e criada em `criarJanelaPrincipal()`.
- Mostrar os botoes e dizer que eles chamam as mesmas operacoes do CRUD, so que com Swing.
- Explicar os componentes `JTextField`, `JComboBox`, `JCheckBox`, `JTable` e `JOptionPane`.
- Mostrar `cadastrarAnimal()`, `listarAnimais()` e `atribuirTratador()` como exemplos de entrada, consulta e vinculacao.
- Explicar que `toArray(...)` e `toString()` foram usados para mostrar os objetos na interface.

Fala base:
- "A interface grafica nao criou outra logica; ela so trocou a forma de interagir com o mesmo CRUD."
- "O sistema primeiro foi feito no console e depois ganhou a `MainGUI.java` para ficar mais visual e facil de usar."

## O que mostrar no codigo
- `Animal.java`: superclasse, atributos comuns, `imprimir()` e `toString()`.
- Subclasses (`Leao.java`, `Elefante.java`, `Macaco.java`, `Cobra.java`): heranca, `super(...)` e metodos proprios.
- `Zoologico.java`: `ArrayList`, busca, cadastro, remocao, listagem e controle de IDs.
- `Main.java`: menu, `switch`, repeticao do programa e leitura de dados.
- `MainGUI.java`: janela, botoes, formularios, tabelas e comunicacao com o CRUD.

## Fechamento do grupo
- Reforcar que o trabalho aplica heranca, polimorfismo, encapsulamento e associacao.
- Dizer que o sistema foi pensado para ser simples, funcional e didatico.
- Destacar que todos os integrantes participam mostrando uma parte diferente do projeto.
