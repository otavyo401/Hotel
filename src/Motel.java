import java.time.Duration;
import java.time.LocalDateTime;

/** Motel: não identifica o cliente e cobra por hora. */
public class Motel extends Estabelecimento {
    private final double valorHora;

    public Motel(String nome, int[] numerosDosQuartos, double valorHora) {
        super(nome, numerosDosQuartos);
        this.valorHora = valorHora;
    }

    /** Devolve o número do quarto ocupado ou SEM_QUARTO. */
    public int checkIn(LocalDateTime entrada) {
        return registrarEntrada(new Estadia(entrada));
    }

    /** Cada hora iniciada é cobrada (mínimo 1). */
    public long calcularHoras(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        long horas = (minutos + 59) / 60; // arredonda pra cima
        return Math.max(1, horas);
    }

    @Override
    protected double calcularValor(Estadia estadia, LocalDateTime saida) {
        return calcularHoras(estadia.getEntrada(), saida) * valorHora;
    }

    @Override
    public String descricaoCobranca() {
        return String.format("R$ %.2f por hora", valorHora);
    }
}
