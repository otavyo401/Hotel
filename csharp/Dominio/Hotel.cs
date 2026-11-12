using System;

namespace HotelMotel.Dominio
{
    /// <summary>Hotel: exige nome e CPF do cliente e cobra por diária.</summary>
    public class Hotel : Estabelecimento
    {
        private const long MinutosPorDia = 24 * 60;

        private readonly decimal valorDiaria;

        public Hotel(string nome, int[] numerosDosQuartos, decimal valorDiaria)
            : base(nome, numerosDosQuartos)
        {
            this.valorDiaria = valorDiaria;
        }

        /// <summary>Devolve o número do quarto ocupado ou SemQuarto.</summary>
        public int CheckIn(Cliente cliente, DateTime entrada)
        {
            return RegistrarEntrada(new EstadiaHotel(cliente, entrada));
        }

        /// <summary>Cada período de 24h iniciado conta como 1 diária (mínimo 1).</summary>
        public long CalcularDiarias(DateTime entrada, DateTime saida)
        {
            long minutos = MinutosEntre(entrada, saida);
            long diarias = (minutos + MinutosPorDia - 1) / MinutosPorDia; // arredonda pra cima
            return Math.Max(1, diarias);
        }

        protected override decimal CalcularValor(Estadia estadia, DateTime saida)
        {
            return CalcularDiarias(estadia.Entrada, saida) * valorDiaria;
        }

        public override string DescricaoCobranca()
        {
            return "R$ " + Formato.Dinheiro(valorDiaria) + " por diária";
        }
    }
}
