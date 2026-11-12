import java.time.LocalDateTime;

/** Representa a permanência de alguém em um quarto (do check-in até o check-out). */
public class Estadia {
    private final LocalDateTime entrada;

    public Estadia(LocalDateTime entrada) {
        if (entrada == null) {
            throw new IllegalArgumentException("Data/hora de entrada é obrigatória.");
        }
        this.entrada = entrada;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }
}
