import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Zoologico zoo = new Zoologico();

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Escolha: ");
            System.out.println();

            switch (opcao) {
                case 1: cadastrarAnimal(); break;
                case 2: zoo.listarAnimais(); break;
                case 3: atualizarAnimal(); break;
                case 4: removerAnimal(); break;
                case 5: cadastrarTratador(); break;
                case 6: zoo.listarTratadores(); break;
                case 7: atualizarTratador(); break;
                case 8: removerTratador(); break;
                case 9: atribuirTratador(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    static void mostrarMenu() {
        System.out.println("\n=== ZOOLÓGICO ===");
        System.out.println("1 - Cadastrar animal");
        System.out.println("2 - Listar animais");
        System.out.println("3 - Atualizar animal");
        System.out.println("4 - Remover animal");
        System.out.println("5 - Cadastrar tratador");
        System.out.println("6 - Listar tratadores");
        System.out.println("7 - Atualizar tratador");
        System.out.println("8 - Remover tratador");
        System.out.println("9 - Atribuir tratador a animal");
        System.out.println("0 - Sair");
    }

    // ----- Animal -----

    static void cadastrarAnimal() {
        System.out.println("Tipo de animal:");
        System.out.println("1 - Leão");
        System.out.println("2 - Elefante");
        System.out.println("3 - Macaco");
        System.out.println("4 - Cobra");
        int tipo = lerInt("Escolha: ");

        String nome = lerString("Nome: ");
        int idade = lerInt("Idade: ");
        int id = zoo.gerarIdAnimal();

        Animal a;
        switch (tipo) {
            case 1: a = new Leao(id, nome, idade); break;
            case 2: a = new Elefante(id, nome, idade); break;
            case 3: a = new Macaco(id, nome, idade); break;
            case 4:
                boolean pec = lerSimNao("Peçonhenta? (s/n): ");
                a = new Cobra(id, nome, idade, pec);
                break;
            default:
                System.out.println("Tipo inválido. Cadastro cancelado.");
                return;
        }

        zoo.cadastrarAnimal(a);
        System.out.println("Animal cadastrado com ID " + id + ".");
    }

    static void atualizarAnimal() {
        int id = lerInt("ID do animal: ");
        Animal a = zoo.buscarAnimalPorId(id);
        if (a == null) {
            System.out.println("Animal não encontrado.");
            return;
        }
        a.nome = lerString("Novo nome: ");
        a.idade = lerInt("Nova idade: ");
        if (a instanceof Cobra) {
            ((Cobra) a).peconhenta = lerSimNao("Peçonhenta? (s/n): ");
        }
        System.out.println("Animal atualizado.");
    }

    static void removerAnimal() {
        int id = lerInt("ID do animal: ");
        if (zoo.removerAnimal(id)) {
            System.out.println("Animal removido.");
        } else {
            System.out.println("Animal não encontrado.");
        }
    }

    // ----- Tratador -----

    static void cadastrarTratador() {
        String nome = lerString("Nome do tratador: ");
        int matricula = zoo.gerarMatricula();
        Tratador t = new Tratador(matricula, nome);
        zoo.cadastrarTratador(t);
        System.out.println("Tratador cadastrado com matrícula " + matricula + ".");
    }

    static void atualizarTratador() {
        int matricula = lerInt("Matrícula: ");
        Tratador t = zoo.buscarTratadorPorMatricula(matricula);
        if (t == null) {
            System.out.println("Tratador não encontrado.");
            return;
        }
        t.nome = lerString("Novo nome: ");
        System.out.println("Tratador atualizado.");
    }

    static void removerTratador() {
        int matricula = lerInt("Matrícula: ");
        if (zoo.removerTratador(matricula)) {
            System.out.println("Tratador removido.");
        } else {
            System.out.println("Tratador não encontrado.");
        }
    }

    static void atribuirTratador() {
        int idAnimal = lerInt("ID do animal: ");
        Animal a = zoo.buscarAnimalPorId(idAnimal);
        if (a == null) {
            System.out.println("Animal não encontrado.");
            return;
        }
        int matricula = lerInt("Matrícula do tratador: ");
        Tratador t = zoo.buscarTratadorPorMatricula(matricula);
        if (t == null) {
            System.out.println("Tratador não encontrado.");
            return;
        }
        a.tratador = t;
        System.out.println("Tratador " + t.nome + " atribuído ao animal " + a.nome + ".");
    }

    // ----- Helpers de leitura -----

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

    static String lerString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    static boolean lerSimNao(String prompt) {
        System.out.print(prompt);
        String resp = sc.nextLine().trim().toLowerCase();
        return resp.startsWith("s");
    }
}
