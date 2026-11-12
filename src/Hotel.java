import java.time.Duration;
import java.time.LocalDateTime;

/** Hotel: exige nome e CPF do cliente e cobra por diária. */
public class Hotel extends Estabelecimento {
    private static final long MINUTOS_POR_DIA = 24 * 60;

    private final double valorDiaria;

    public Hotel(String nome, int[] numerosDosQuartos, double valorDiaria) {
        super(nome, numerosDosQuartos);
        this.valorDiaria = valorDiaria;
    }

    /** Devolve o número do quarto ocupado ou SEM_QUARTO. */
    public int checkIn(Cliente cliente, LocalDateTime entrada) {
        return registrarEntrada(new EstadiaHotel(cliente, entrada));
    }

    /** Cada período de 24h iniciado conta como 1 diária (mínimo 1). */
    public long calcularDiarias(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        long diarias = (minutos + MINUTOS_POR_DIA - 1) / MINUTOS_POR_DIA; // arredonda pra cima
        return Math.max(1, diarias);
    }

    @Override
    protected double calcularValor(Estadia estadia, LocalDateTime saida) {
        return calcularDiarias(estadia.getEntrada(), saida) * valorDiaria;
    }

    @Override
    public String descricaoCobranca() {
        return String.format("R$ %.2f por diária", valorDiaria);
    }
}
