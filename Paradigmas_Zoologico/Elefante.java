class Elefante extends Animal {

    Elefante(int id, String nome, int idade) {
        super(id, nome, "Elefante", idade);
    }

    void tomarBanho() {
        System.out.println(nome + " está tomando banho com a tromba");
    }

    void imprimir() {
        System.out.println("=== Elefante ===");
        super.imprimir();
        comer();
        dormir();
        tomarBanho();
        System.out.println();
    }
}
