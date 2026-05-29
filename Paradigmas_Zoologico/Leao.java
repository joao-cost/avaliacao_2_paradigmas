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
