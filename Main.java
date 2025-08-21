import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Classe Usuario
class Usuario {
    private String nome;
    private String email;
    private int idade;
    private List<Evento> eventosConfirmados = new ArrayList<>();

    public Usuario(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public String getNome() { return nome; }

    public void confirmarPresenca(Evento e) {
        eventosConfirmados.add(e);
    }

    public void cancelarPresenca(Evento e) {
        eventosConfirmados.remove(e);
    }

    public void listarEventosConfirmados() {
        if (eventosConfirmados.isEmpty()) {
            System.out.println("Nenhum evento confirmado.");
        } else {
            System.out.println("Eventos confirmados:");
            for (Evento e : eventosConfirmados) {
                System.out.println("- " + e.getNome() + " em " + e.getHorario());
            }
        }
    }
}

// Classe Evento
class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return nome + " | " + categoria + " | " + horario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                " | " + endereco + " | " + descricao;
    }
}

// Classe principal do sistema
public class Main {
    private static List<Evento> eventos = new ArrayList<>();
    private static Usuario usuario;
    private static final String FILE_NAME = "events.data";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        carregarEventos();

        System.out.println("=== Cadastro de Usuário ===");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine(); // limpar buffer
        usuario = new Usuario(nome, email, idade);

        int opcao;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Confirmar presença em evento");
            System.out.println("4 - Cancelar presença");
            System.out.println("5 - Listar eventos confirmados");
            System.out.println("6 - Mostrar eventos atuais");
            System.out.println("7 - Mostrar eventos passados");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarEvento(sc);
                case 2 -> listarEventos();
                case 3 -> confirmarPresenca(sc);
                case 4 -> cancelarPresenca(sc);
                case 5 -> usuario.listarEventosConfirmados();
                case 6 -> listarEventosAtuais();
                case 7 -> listarEventosPassados();
                case 0 -> salvarEventos();
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarEvento(Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Categoria (Festa, Show, Esporte...): ");
        String categoria = sc.nextLine();
        System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
        String dataStr = sc.nextLine();
        LocalDateTime horario = LocalDateTime.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        Evento e = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(e);
        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            int i = 1;
            for (Evento e : eventos) {
                System.out.println(i++ + " - " + e);
            }
        }
    }

    private static void confirmarPresenca(Scanner sc) {
        listarEventos();
        System.out.print("Digite o número do evento: ");
        int idx = sc.nextInt() - 1;
        if (idx >= 0 && idx < eventos.size()) {
            usuario.confirmarPresenca(eventos.get(idx));
            System.out.println("Presença confirmada!");
        } else {
            System.out.println("Evento inválido!");
        }
    }

    private static void cancelarPresenca(Scanner sc) {
        listarEventos();
        System.out.print("Digite o número do evento para cancelar: ");
        int idx = sc.nextInt() - 1;
        if (idx >= 0 && idx < eventos.size()) {
            usuario.cancelarPresenca(eventos.get(idx));
            System.out.println("Presença cancelada!");
        } else {
            System.out.println("Evento inválido!");
        }
    }

    private static void listarEventosAtuais() {
        LocalDateTime agora = LocalDateTime.now();
        for (Evento e : eventos) {
            if (e.getHorario().isBefore(agora.plusMinutes(1)) && e.getHorario().isAfter(agora.minusMinutes(1))) {
                System.out.println("Agora: " + e);
            }
        }
    }

    private static void listarEventosPassados() {
        LocalDateTime agora = LocalDateTime.now();
        for (Evento e : eventos) {
            if (e.getHorario().isBefore(agora)) {
                System.out.println("Já ocorreu: " + e);
            }
        }
    }

    private static void salvarEventos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Evento e : eventos) {
                pw.println(e.getNome() + ";" + e.getEndereco() + ";" + e.getCategoria() + ";" +
                        e.getHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ";" + e.getDescricao());
            }
            System.out.println("Eventos salvos em " + FILE_NAME);
        } catch (IOException ex) {
            System.out.println("Erro ao salvar eventos!");
        }
    }

    private static void carregarEventos() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                LocalDateTime horario = LocalDateTime.parse(dados[3], DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                Evento e = new Evento(dados[0], dados[1], dados[2], horario, dados[4]);
                eventos.add(e);
            }
            System.out.println("Eventos carregados do arquivo.");
        } catch (IOException e) {
            System.out.println("Nenhum evento salvo encontrado.");
        }
    }
}
