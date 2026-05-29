class Animal {
    int id;
    String nome;
    String especie;
    int idade;
    Tratador tratador;

    Animal(int id, String nome, String especie, int idade) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
    }

    void comer() {
        System.out.println(nome + " está comendo");
    }

    void dormir() {
        System.out.println(nome + " está dormindo");
    }

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

    // Usado pelos JComboBox da interface gráfica para mostrar o animal de forma legível
    public String toString() {
        return "ID " + id + " - " + nome + " (" + especie + ")";
    }
}
