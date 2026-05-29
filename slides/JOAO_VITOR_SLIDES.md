# Slides — João Vitor: Paradigma Imperativo/Procedural vs Orientado a Objetos

---

## Slide 1 — Título

João Vitor — Paradigmas: Imperativo/Procedural x Orientado a Objetos

Notas do apresentador:
- Apresente-se brevemente e diga o objetivo: explicar a diferença entre os paradigmas e relacionar com o código do projeto.

---

## Slide 2 — Objetivo da explicação

- Mostrar a diferença de pensamento e organização entre os paradigmas.
- Dar um exemplo simples em código procedural (estilo C) e o equivalente em OO (Java).
- Apontar onde o projeto aplica OO.

Notas do apresentador:
- Fale em até 1 minuto: objetivo e roteiro rápido do que vai mostrar.

---

## Slide 3 — Paradigma Imperativo / Procedural (resumo)

- Foco em: sequência de comandos e funções.
- Organização: dados separados de funções; programa como fluxo de passos.
- Uso comum: scripts, programas pequenos, código C clássico.

Notas do apresentador:
- Explique que o programador pensa em passos: ler, processar, imprimir; funções são agrupamentos de passos.

---

## Slide 4 — Exemplo (estilo C, procedural)

```c
#include <stdio.h>

typedef struct { char nome[50]; int idade; } Animal;

void imprimir(Animal a) {
    printf("Nome: %s\n", a.nome);
    printf("Idade: %d\n", a.idade);
}

int main() {
    Animal a = {"Simba", 7};
    imprimir(a);
    return 0;
}
```

Notas do apresentador:
- A estrutura `Animal` existe, mas o comportamento `imprimir` é uma função separada.
- Mostrar que os dados e as funcoes ficam separados; controle é feito pela chamada de funções.

---

## Slide 5 — Paradigma Orientado a Objetos (resumo)

- Foco em: objetos que encapsulam dados e comportamentos.
- Organização: classes reune dados (atributos) e métodos (comportamentos).
- Benefícios: encapsulamento, herança, polimorfismo, reutilização.

Notas do apresentador:
- Diga que OO facilita modelar coisas do mundo real como `Animal`, `Tratador`, `Zoologico`.

---

## Slide 6 — Exemplo (Java, OO simplificado)

```java
public class Animal {
    String nome;
    int idade;

    Animal(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    void imprimir() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}

// uso
Animal a = new Animal("Simba", 7);
a.imprimir();
```

Notas do apresentador:
- `imprimir()` é parte do objeto; chamamos o comportamento diretamente no objeto.
- Mostre como a chamada `a.imprimir()` fica mais natural sem separar dados/comportamento.

---

## Slide 7 — Comparação prática (resumo rápido)

- Procedural: foco em funcoes, controle por fluxo, dados passados como argumentos.
- OO: foco em objetos, encapsulamento, responsabilidades bem divididas.
- Manutenção: OO tende a escalar melhor em sistemas maiores.

Notas do apresentador:
- Cite um exemplo prático: adicionar novo comportamento — em OO pode-se sobrescrever em subclasses; em procedural pode significar alterar muitas funcoes.

---

## Slide 8 — Como isso aparece no nosso projeto

- `Animal.java` + subclasses (`Leao.java`, `Cobra.java`, etc.) => exemplo claro de OO.
- `Zoologico.java` => gerencia listas (`ArrayList`), procura, cadastra e remove objetos.
- `Main.java` => mostra a parte procedural/imperativa do fluxo: menu, leitura, chamadas de funções.
- `MainGUI.java` => mesma logica, mas com eventos de GUI (Swing).

Referências de arquivo (abra durante a apresentação):
- [Paradigmas_Zoologico/Main.java](Paradigmas_Zoologico/Main.java)
- [Paradigmas_Zoologico/Animal.java](Paradigmas_Zoologico/Animal.java)
- [Paradigmas_Zoologico/Zoologico.java](Paradigmas_Zoologico/Zoologico.java)
- [Paradigmas_Zoologico/MainGUI.java](Paradigmas_Zoologico/MainGUI.java)

Notas do apresentador:
- Explique que `Main.java` ilustra o fluxo (imperativo) enquanto as classes ilustram a modelagem OO.

---

## Slide 9 — Pontos para mostrar no código (ao vivo)

- Abrir `Animal.imprimir()` e mostrar `toString()`.
- Abrir um construtor de subclasse (ex.: `Leao.java`) e apontar `super(...)`.
- Abrir `Zoologico.gerarIdAnimal()` e `cadastrarAnimal()` para mostrar o CRUD.
- Abrir `Main.java` para mostrar o `switch` do menu e `cadastrarAnimal()` (fluxo imperativo).

Notas do apresentador:
- Fale exatamente onde clicar/abrir para economizar tempo na apresentação.

---

## Slide 10 — Dicas de fala (30-60s)

- Comece com uma frase objetiva: "No paradigma procedural, pensamos em passos; no OO, pensamos em objetos." 
- Mostre a diferença com os trechos de código.
- Use o projeto como exemplo: o menu (procedural) vs as classes (OO).
- Termine dizendo por que escolher OO aqui foi vantajoso.

---

## Slide 11 — Perguntas

- Abra para perguntas do avaliador.

Notas do apresentador:
- Tenha em mente exemplos simples para responder: por que `super` foi usado; por que `ArrayList` foi escolhido; quando preferir console vs GUI.

---

*Arquivo salvo em `slides/JOAO_VITOR_SLIDES.md`*