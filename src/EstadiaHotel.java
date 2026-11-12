import java.time.LocalDateTime;

/** Estadia de hotel: além da entrada, guarda o cliente (nome e CPF). */
public class EstadiaHotel extends Estadia {
    private final Cliente cliente;

    public EstadiaHotel(Cliente cliente, LocalDateTime entrada) {
        super(entrada);
        if (cliente == null) {
            throw new IllegalArgumentException("Hotel precisa dos dados do cliente.");
        }
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
