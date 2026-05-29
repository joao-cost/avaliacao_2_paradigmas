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
