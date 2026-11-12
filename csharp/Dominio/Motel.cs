using System;

namespace HotelMotel.Dominio
{
    /// <summary>Motel: não identifica o cliente e cobra por hora.</summary>
    public class Motel : Estabelecimento
    {
        private readonly decimal valorHora;

        public Motel(string nome, int[] numerosDosQuartos, decimal valorHora)
            : base(nome, numerosDosQuartos)
        {
            this.valorHora = valorHora;
        }

        /// <summary>Devolve o número do quarto ocupado ou SemQuarto.</summary>
        public int CheckIn(DateTime entrada)
        {
            return RegistrarEntrada(new Estadia(entrada));
        }

        /// <summary>Cada hora iniciada é cobrada (mínimo 1).</summary>
        public long CalcularHoras(DateTime entrada, DateTime saida)
        {
            long minutos = MinutosEntre(entrada, saida);
            long horas = (minutos + 59) / 60; // arredonda pra cima
            return Math.Max(1, horas);
        }

        protected override decimal CalcularValor(Estadia estadia, DateTime saida)
        {
            return CalcularHoras(estadia.Entrada, saida) * valorHora;
        }

        public override string DescricaoCobranca()
        {
            return "R$ " + Formato.Dinheiro(valorHora) + " por hora";
        }
    }
}
