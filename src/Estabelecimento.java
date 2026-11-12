import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Superclasse abstrata de Hotel e Motel.
 * Concentra o que é comum: quartos, busca de quarto livre e check-out.
 * A forma de cobrança fica a cargo de cada subclasse (calcularValor).
 */
public abstract class Estabelecimento {
    /** Valor devolvido pelo check-in quando não há quarto disponível. */
    public static final int SEM_QUARTO = -1;

    private final String nome;
    private final List<Quarto> quartos = new ArrayList<>();

    protected Estabelecimento(String nome, int[] numerosDosQuartos) {
        this.nome = nome;
        for (int numero : numerosDosQuartos) {
            quartos.add(new Quarto(numero));
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Quarto> getQuartos() {
        return Collections.unmodifiableList(quartos);
    }

    /** Primeiro quarto livre, ou null se todos estiverem ocupados. */
    protected Quarto buscarQuartoDisponivel() {
        for (Quarto q : quartos) {
            if (q.isDisponivel()) {
                return q;
            }
        }
        return null;
    }

    /** Ocupa um quarto livre com a estadia e devolve seu número (ou SEM_QUARTO). */
    protected int registrarEntrada(Estadia estadia) {
        Quarto quarto = buscarQuartoDisponivel();
        if (quarto == null) {
            return SEM_QUARTO;
        }
        quarto.ocupar(estadia);
        return quarto.getNumero();
    }

    protected Quarto buscarQuarto(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }

    /**
     * Faz o check-out do quarto informado e devolve o valor a pagar.
     * Lança IllegalArgumentException se o quarto não existe, está vazio
     * ou se a saída for anterior à entrada.
     */
    public double checkOut(int numeroQuarto, LocalDateTime saida) {
        Quarto quarto = buscarQuarto(numeroQuarto);
        if (quarto == null) {
            throw new IllegalArgumentException("Quarto " + numeroQuarto + " não existe no " + nome + ".");
        }
        if (quarto.isDisponivel()) {
            throw new IllegalArgumentException("Quarto " + numeroQuarto + " não está ocupado.");
        }
        Estadia estadia = quarto.getEstadiaAtual();
        if (saida.isBefore(estadia.getEntrada())) {
            throw new IllegalArgumentException("A saída não pode ser anterior à entrada.");
        }
        double valor = calcularValor(estadia, saida);
        quarto.liberar();
        return valor;
    }

    /** Cada tipo de estabelecimento cobra de um jeito (polimorfismo). */
    protected abstract double calcularValor(Estadia estadia, LocalDateTime saida);

    /** Descrição da forma de cobrança, para exibição. */
    public abstract String descricaoCobranca();
}
