import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Menu de operações do sistema de Hotel e Motel. */
public class Main {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Scanner entrada = new Scanner(System.in);

    private static final Hotel hotel =
            new Hotel("Hotel Central", new int[]{101, 102, 103, 104, 105}, 250.00);
    private static final Motel motel =
            new Motel("Motel Estrela", new int[]{1, 2, 3, 4, 5}, 50.00);

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Opção: ");
            try {
                switch (opcao) {
                    case 1: checkInHotel(); break;
                    case 2: checkOut(hotel); break;
                    case 3: checkInMotel(); break;
                    case 4: checkOut(motel); break;
                    case 5: listarQuartos(hotel); break;
                    case 6: listarQuartos(motel); break;
                    case 0: System.out.println("Encerrando o sistema."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========== SISTEMA HOTEL / MOTEL ==========");
        System.out.println("1 - Hotel: check-in");
        System.out.println("2 - Hotel: check-out");
        System.out.println("3 - Motel: check-in");
        System.out.println("4 - Motel: check-out");
        System.out.println("5 - Hotel: situação dos quartos");
        System.out.println("6 - Motel: situação dos quartos");
        System.out.println("0 - Sair");
    }

    private static void checkInHotel() {
        String nome = lerTexto("Nome do cliente: ");
        String cpf = lerTexto("CPF do cliente: ");
        Cliente cliente = new Cliente(nome, cpf);
        LocalDateTime quando = lerDataHora("Data/hora de entrada");
        int quarto = hotel.checkIn(cliente, quando);
        mostrarResultadoCheckIn(hotel, quarto);
    }

    private static void checkInMotel() {
        LocalDateTime quando = lerDataHora("Data/hora de entrada");
        int quarto = motel.checkIn(quando);
        mostrarResultadoCheckIn(motel, quarto);
    }

    private static void mostrarResultadoCheckIn(Estabelecimento e, int quarto) {
        if (quarto == Estabelecimento.SEM_QUARTO) {
            System.out.println("Não há quartos disponíveis no " + e.getNome() + ".");
        } else {
            System.out.println("Check-in realizado no " + e.getNome() + ". Quarto: " + quarto);
        }
    }

    /** Mesmo método serve para Hotel e Motel (polimorfismo no cálculo do valor). */
    private static void checkOut(Estabelecimento e) {
        int numero = lerInteiro("Número do quarto: ");
        LocalDateTime quando = lerDataHora("Data/hora de saída");
        double valor = e.checkOut(numero, quando);
        System.out.printf("Check-out do quarto %d realizado (%s).%n", numero, e.descricaoCobranca());
        System.out.printf("Valor a pagar: R$ %.2f%n", valor);
    }

    private static void listarQuartos(Estabelecimento e) {
        System.out.println("--- " + e.getNome() + " (" + e.descricaoCobranca() + ") ---");
        for (Quarto q : e.getQuartos()) {
            if (q.isDisponivel()) {
                System.out.println("Quarto " + q.getNumero() + ": livre");
            } else {
                Estadia est = q.getEstadiaAtual();
                String linha = "Quarto " + q.getNumero() + ": ocupado desde " + est.getEntrada().format(FORMATO);
                if (est instanceof EstadiaHotel) {
                    linha += " - " + ((EstadiaHotel) est).getCliente();
                }
                System.out.println(linha);
            }
        }
    }

    // ---------- leitura do teclado ----------

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            String linha = lerTexto(mensagem).trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    /** Lê data/hora no formato dd/MM/yyyy HH:mm; Enter vazio = agora. */
    private static LocalDateTime lerDataHora(String mensagem) {
        while (true) {
            String linha = lerTexto(mensagem + " (dd/MM/aaaa HH:mm, Enter = agora): ").trim();
            if (linha.isEmpty()) {
                return LocalDateTime.now().withSecond(0).withNano(0);
            }
            try {
                return LocalDateTime.parse(linha, FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Exemplo: 12/11/2026 14:30");
            }
        }
    }
}
