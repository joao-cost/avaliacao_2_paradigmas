import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

public class MainGUI {

    // Compartilhado entre todos os métodos: mesma lógica do Main.java do console.
    static Zoologico zoo = new Zoologico();
    static JFrame janelaPrincipal;

    public static void main(String[] args) {
        // SwingUtilities.invokeLater garante que a interface seja criada
        // na thread correta do Swing (Event Dispatch Thread - EDT).
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                criarJanelaPrincipal();
            }
        });
    }

    // ----- Janela principal -----

    static void criarJanelaPrincipal() {
        janelaPrincipal = new JFrame("Zoológico - Sistema de Cadastro");
        janelaPrincipal.setSize(500, 450);
        janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaPrincipal.setLayout(new BorderLayout());

        // Título no topo
        JLabel titulo = new JLabel("=== ZOOLÓGICO ===", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        janelaPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel central com os botões (5 linhas x 2 colunas)
        JPanel painelBotoes = new JPanel(new GridLayout(5, 2, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        adicionarBotao(painelBotoes, "Cadastrar Animal", e -> cadastrarAnimal());
        adicionarBotao(painelBotoes, "Listar Animais", e -> listarAnimais());
        adicionarBotao(painelBotoes, "Atualizar Animal", e -> atualizarAnimal());
        adicionarBotao(painelBotoes, "Remover Animal", e -> removerAnimal());
        adicionarBotao(painelBotoes, "Cadastrar Tratador", e -> cadastrarTratador());
        adicionarBotao(painelBotoes, "Listar Tratadores", e -> listarTratadores());
        adicionarBotao(painelBotoes, "Atualizar Tratador", e -> atualizarTratador());
        adicionarBotao(painelBotoes, "Remover Tratador", e -> removerTratador());
        adicionarBotao(painelBotoes, "Atribuir Tratador", e -> atribuirTratador());
        adicionarBotao(painelBotoes, "Sair", e -> System.exit(0));

        janelaPrincipal.add(painelBotoes, BorderLayout.CENTER);
        janelaPrincipal.setLocationRelativeTo(null); // centraliza na tela
        janelaPrincipal.setVisible(true);
    }

    // Atalho para criar botão + adicionar a ação (ActionListener) de clique
    static void adicionarBotao(JPanel painel, String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        painel.add(botao);
    }

    // ----- Animal: CRUD -----

    static void cadastrarAnimal() {
        // Componentes do formulário
        String[] tipos = {"Leão", "Elefante", "Macaco", "Cobra"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);
        JTextField tfNome = new JTextField(15);
        JTextField tfIdade = new JTextField(5);
        JCheckBox cbPec = new JCheckBox("Peçonhenta (somente para Cobra)");

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("Tipo:"));
        painel.add(cbTipo);
        painel.add(new JLabel("Nome:"));
        painel.add(tfNome);
        painel.add(new JLabel("Idade:"));
        painel.add(tfIdade);
        painel.add(new JLabel(""));
        painel.add(cbPec);

        int resultado = JOptionPane.showConfirmDialog(janelaPrincipal, painel,
                "Cadastrar Animal", JOptionPane.OK_CANCEL_OPTION);

        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        String nome = tfNome.getText().trim();
        String idadeStr = tfIdade.getText().trim();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            mostrarErro("Preencha o nome e a idade.");
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException ex) {
            mostrarErro("Idade deve ser um número inteiro.");
            return;
        }

        int tipo = cbTipo.getSelectedIndex();
        int id = zoo.gerarIdAnimal();

        Animal a;
        switch (tipo) {
            case 0: a = new Leao(id, nome, idade); break;
            case 1: a = new Elefante(id, nome, idade); break;
            case 2: a = new Macaco(id, nome, idade); break;
            case 3: a = new Cobra(id, nome, idade, cbPec.isSelected()); break;
            default: return;
        }

        zoo.cadastrarAnimal(a);
        mostrarInfo("Animal cadastrado com ID " + id + ".");
    }

    static void listarAnimais() {
        if (zoo.animais.isEmpty()) {
            mostrarInfo("Nenhum animal cadastrado.");
            return;
        }

        String[] colunas = {"ID", "Espécie", "Nome", "Idade", "Tratador", "Detalhes"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Animal a : zoo.animais) {
            String tratador = (a.tratador != null) ? a.tratador.nome : "-";
            String detalhes = "";
            if (a instanceof Cobra) {
                detalhes = ((Cobra) a).peconhenta ? "Peçonhenta" : "Não peçonhenta";
            }
            modelo.addRow(new Object[]{a.id, a.especie, a.nome, a.idade, tratador, detalhes});
        }

        JTable tabela = new JTable(modelo);
        tabela.setEnabled(false); // só leitura
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(600, 250));

        JOptionPane.showMessageDialog(janelaPrincipal, scroll, "Lista de Animais",
                JOptionPane.PLAIN_MESSAGE);
    }

    static void atualizarAnimal() {
        if (zoo.animais.isEmpty()) {
            mostrarInfo("Nenhum animal cadastrado.");
            return;
        }

        Animal a = selecionarAnimal("Selecione o animal a atualizar:");
        if (a == null) {
            return;
        }

        JTextField tfNome = new JTextField(a.nome, 15);
        JTextField tfIdade = new JTextField(String.valueOf(a.idade), 5);

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("Nome:"));
        painel.add(tfNome);
        painel.add(new JLabel("Idade:"));
        painel.add(tfIdade);

        JCheckBox cbPec = null;
        if (a instanceof Cobra) {
            cbPec = new JCheckBox("Peçonhenta", ((Cobra) a).peconhenta);
            painel.add(new JLabel(""));
            painel.add(cbPec);
        }

        int resp = JOptionPane.showConfirmDialog(janelaPrincipal, painel,
                "Atualizar Animal", JOptionPane.OK_CANCEL_OPTION);

        if (resp != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            a.nome = tfNome.getText().trim();
            a.idade = Integer.parseInt(tfIdade.getText().trim());
            if (a instanceof Cobra && cbPec != null) {
                ((Cobra) a).peconhenta = cbPec.isSelected();
            }
            mostrarInfo("Animal atualizado.");
        } catch (NumberFormatException ex) {
            mostrarErro("Idade inválida.");
        }
    }

    static void removerAnimal() {
        if (zoo.animais.isEmpty()) {
            mostrarInfo("Nenhum animal cadastrado.");
            return;
        }

        Animal a = selecionarAnimal("Selecione o animal a remover:");
        if (a == null) {
            return;
        }

        int conf = JOptionPane.showConfirmDialog(janelaPrincipal,
                "Remover " + a.nome + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) {
            return;
        }

        zoo.removerAnimal(a.id);
        mostrarInfo("Animal removido.");
    }

    // ----- Tratador: CRUD -----

    static void cadastrarTratador() {
        String nome = JOptionPane.showInputDialog(janelaPrincipal, "Nome do tratador:");
        if (nome == null) {
            return;
        }
        nome = nome.trim();
        if (nome.isEmpty()) {
            mostrarErro("Nome não pode ser vazio.");
            return;
        }

        int matricula = zoo.gerarMatricula();
        Tratador t = new Tratador(matricula, nome);
        zoo.cadastrarTratador(t);
        mostrarInfo("Tratador cadastrado com matrícula " + matricula + ".");
    }

    static void listarTratadores() {
        if (zoo.tratadores.isEmpty()) {
            mostrarInfo("Nenhum tratador cadastrado.");
            return;
        }

        String[] colunas = {"Matrícula", "Nome"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Tratador t : zoo.tratadores) {
            modelo.addRow(new Object[]{t.matricula, t.nome});
        }

        JTable tabela = new JTable(modelo);
        tabela.setEnabled(false);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(janelaPrincipal, scroll, "Lista de Tratadores",
                JOptionPane.PLAIN_MESSAGE);
    }

    static void atualizarTratador() {
        if (zoo.tratadores.isEmpty()) {
            mostrarInfo("Nenhum tratador cadastrado.");
            return;
        }

        Tratador t = selecionarTratador("Selecione o tratador a atualizar:");
        if (t == null) {
            return;
        }

        String novoNome = JOptionPane.showInputDialog(janelaPrincipal, "Novo nome:", t.nome);
        if (novoNome == null) {
            return;
        }
        novoNome = novoNome.trim();
        if (novoNome.isEmpty()) {
            mostrarErro("Nome não pode ser vazio.");
            return;
        }

        t.nome = novoNome;
        mostrarInfo("Tratador atualizado.");
    }

    static void removerTratador() {
        if (zoo.tratadores.isEmpty()) {
            mostrarInfo("Nenhum tratador cadastrado.");
            return;
        }

        Tratador t = selecionarTratador("Selecione o tratador a remover:");
        if (t == null) {
            return;
        }

        int conf = JOptionPane.showConfirmDialog(janelaPrincipal,
                "Remover " + t.nome + "?\n(Os animais que ele cuidava ficarão sem tratador)",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) {
            return;
        }

        zoo.removerTratador(t.matricula);
        mostrarInfo("Tratador removido.");
    }

    // ----- Vínculo Animal <-> Tratador -----

    static void atribuirTratador() {
        if (zoo.animais.isEmpty()) {
            mostrarErro("Cadastre pelo menos um animal antes.");
            return;
        }
        if (zoo.tratadores.isEmpty()) {
            mostrarErro("Cadastre pelo menos um tratador antes.");
            return;
        }

        JComboBox<Animal> cbAnimal = new JComboBox<>(zoo.animais.toArray(new Animal[0]));
        JComboBox<Tratador> cbTratador = new JComboBox<>(zoo.tratadores.toArray(new Tratador[0]));

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("Animal:"));
        painel.add(cbAnimal);
        painel.add(new JLabel("Tratador:"));
        painel.add(cbTratador);

        int resp = JOptionPane.showConfirmDialog(janelaPrincipal, painel,
                "Atribuir Tratador", JOptionPane.OK_CANCEL_OPTION);

        if (resp != JOptionPane.OK_OPTION) {
            return;
        }

        Animal a = (Animal) cbAnimal.getSelectedItem();
        Tratador t = (Tratador) cbTratador.getSelectedItem();
        a.tratador = t;
        mostrarInfo("Tratador " + t.nome + " atribuído ao animal " + a.nome + ".");
    }

    // ----- Helpers genéricos -----

    static Animal selecionarAnimal(String mensagem) {
        Animal[] arr = zoo.animais.toArray(new Animal[0]);
        return (Animal) JOptionPane.showInputDialog(janelaPrincipal, mensagem,
                "Selecionar Animal", JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
    }

    static Tratador selecionarTratador(String mensagem) {
        Tratador[] arr = zoo.tratadores.toArray(new Tratador[0]);
        return (Tratador) JOptionPane.showInputDialog(janelaPrincipal, mensagem,
                "Selecionar Tratador", JOptionPane.QUESTION_MESSAGE, null, arr, arr[0]);
    }

    static void mostrarInfo(String msg) {
        JOptionPane.showMessageDialog(janelaPrincipal, msg, "Informação",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(janelaPrincipal, msg, "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}
