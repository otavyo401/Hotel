/** Quarto de casal (único tipo de quarto do sistema). */
public class Quarto {
    private final int numero;
    private Estadia estadiaAtual; // null = quarto livre

    public Quarto(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return estadiaAtual == null;
    }

    public Estadia getEstadiaAtual() {
        return estadiaAtual;
    }

    /** Garante que um quarto ocupado nunca recebe outro cliente. */
    public void ocupar(Estadia estadia) {
        if (!isDisponivel()) {
            throw new IllegalStateException("O quarto " + numero + " já está ocupado.");
        }
        this.estadiaAtual = estadia;
    }

    /** Libera o quarto e devolve a estadia que estava nele. */
    public Estadia liberar() {
        if (isDisponivel()) {
            throw new IllegalStateException("O quarto " + numero + " não está ocupado.");
        }
        Estadia estadia = estadiaAtual;
        estadiaAtual = null;
        return estadia;
    }
}
