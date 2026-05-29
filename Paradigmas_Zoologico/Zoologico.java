import java.util.ArrayList;

class Zoologico {
    ArrayList<Animal> animais;
    ArrayList<Tratador> tratadores;
    int proxIdAnimal;
    int proxMatricula;

    Zoologico() {
        this.animais = new ArrayList<>();
        this.tratadores = new ArrayList<>();
        this.proxIdAnimal = 1;
        this.proxMatricula = 1;
    }

    // ----- CRUD Animal -----

    int gerarIdAnimal() {
        return proxIdAnimal++;
    }

    void cadastrarAnimal(Animal a) {
        animais.add(a);
    }

    Animal buscarAnimalPorId(int id) {
        for (Animal a : animais) {
            if (a.id == id) {
                return a;
            }
        }
        return null;
    }

    boolean removerAnimal(int id) {
        Animal a = buscarAnimalPorId(id);
        if (a == null) {
            return false;
        }
        animais.remove(a);
        return true;
    }

    void listarAnimais() {
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }
        for (Animal a : animais) {
            a.imprimir();
        }
    }

    // ----- CRUD Tratador -----

    int gerarMatricula() {
        return proxMatricula++;
    }

    void cadastrarTratador(Tratador t) {
        tratadores.add(t);
    }

    Tratador buscarTratadorPorMatricula(int matricula) {
        for (Tratador t : tratadores) {
            if (t.matricula == matricula) {
                return t;
            }
        }
        return null;
    }

    boolean removerTratador(int matricula) {
        Tratador t = buscarTratadorPorMatricula(matricula);
        if (t == null) {
            return false;
        }
        // Desvincular o tratador dos animais que ele cuidava
        for (Animal a : animais) {
            if (a.tratador == t) {
                a.tratador = null;
            }
        }
        tratadores.remove(t);
        return true;
    }

    void listarTratadores() {
        if (tratadores.isEmpty()) {
            System.out.println("Nenhum tratador cadastrado.");
            return;
        }
        for (Tratador t : tratadores) {
            t.imprimir();
            System.out.println();
        }
    }
}
