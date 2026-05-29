class Macaco extends Animal {

    Macaco(int id, String nome, int idade) {
        super(id, nome, "Macaco", idade);
    }

    void pular() {
        System.out.println(nome + " está pulando entre os galhos");
    }

    void imprimir() {
        System.out.println("=== Macaco ===");
        super.imprimir();
        comer();
        dormir();
        pular();
        System.out.println();
    }
}
