class Tratador {
    int matricula;
    String nome;

    Tratador(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    void imprimir() {
        System.out.println("Matrícula: " + matricula);
        System.out.println("Nome: " + nome);
    }

    // Usado pelos JComboBox da interface gráfica para mostrar o tratador de forma legível
    public String toString() {
        return "Matrícula " + matricula + " - " + nome;
    }
}
