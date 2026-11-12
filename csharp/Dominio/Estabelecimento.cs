using System;
using System.Collections.Generic;

namespace HotelMotel.Dominio
{
    /// <summary>
    /// Superclasse abstrata de Hotel e Motel.
    /// Concentra o que é comum: quartos, busca de quarto livre e check-out.
    /// A forma de cobrança fica a cargo de cada subclasse (CalcularValor).
    /// </summary>
    public abstract class Estabelecimento
    {
        /// <summary>Valor devolvido pelo check-in quando não há quarto disponível.</summary>
        public const int SemQuarto = -1;

        private readonly string nome;
        private readonly List<Quarto> quartos = new List<Quarto>();

        protected Estabelecimento(string nome, int[] numerosDosQuartos)
        {
            this.nome = nome;
            foreach (int numero in numerosDosQuartos)
                quartos.Add(new Quarto(numero));
        }

        public string Nome { get { return nome; } }
        public IReadOnlyList<Quarto> Quartos { get { return quartos.AsReadOnly(); } }

        /// <summary>Primeiro quarto livre, ou null se todos estiverem ocupados.</summary>
        protected Quarto BuscarQuartoDisponivel()
        {
            foreach (Quarto q in quartos)
                if (q.Disponivel)
                    return q;
            return null;
        }

        /// <summary>Ocupa um quarto livre com a estadia e devolve seu número (ou SemQuarto).</summary>
        protected int RegistrarEntrada(Estadia estadia)
        {
            Quarto quarto = BuscarQuartoDisponivel();
            if (quarto == null)
                return SemQuarto;
            quarto.Ocupar(estadia);
            return quarto.Numero;
        }

        protected Quarto BuscarQuarto(int numero)
        {
            foreach (Quarto q in quartos)
                if (q.Numero == numero)
                    return q;
            return null;
        }

        /// <summary>
        /// Faz o check-out do quarto informado e devolve o valor a pagar.
        /// Lança ArgumentException se o quarto não existe, está vazio
        /// ou se a saída for anterior à entrada.
        /// </summary>
        public decimal CheckOut(int numeroQuarto, DateTime saida)
        {
            Quarto quarto = BuscarQuarto(numeroQuarto);
            if (quarto == null)
                throw new ArgumentException("Quarto " + numeroQuarto + " não existe no " + nome + ".");
            if (quarto.Disponivel)
                throw new ArgumentException("Quarto " + numeroQuarto + " não está ocupado.");
            Estadia estadia = quarto.EstadiaAtual;
            if (saida < estadia.Entrada)
                throw new ArgumentException("A saída não pode ser anterior à entrada.");
            decimal valor = CalcularValor(estadia, saida);
            quarto.Liberar();
            return valor;
        }

        /// <summary>Minutos inteiros entre a entrada e a saída.</summary>
        protected static long MinutosEntre(DateTime entrada, DateTime saida)
        {
            return (long)Math.Floor((saida - entrada).TotalMinutes);
        }

        /// <summary>Cada tipo de estabelecimento cobra de um jeito (polimorfismo).</summary>
        protected abstract decimal CalcularValor(Estadia estadia, DateTime saida);

        /// <summary>Descrição da forma de cobrança, para exibição.</summary>
        public abstract string DescricaoCobranca();
    }
}
