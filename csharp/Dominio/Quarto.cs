using System;

namespace HotelMotel.Dominio
{
    /// <summary>Quarto de casal (único tipo de quarto do sistema).</summary>
    public class Quarto
    {
        private readonly int numero;
        private Estadia estadiaAtual; // null = quarto livre

        public Quarto(int numero)
        {
            this.numero = numero;
        }

        public int Numero { get { return numero; } }
        public bool Disponivel { get { return estadiaAtual == null; } }
        public Estadia EstadiaAtual { get { return estadiaAtual; } }

        /// <summary>Garante que um quarto ocupado nunca recebe outro cliente.</summary>
        public void Ocupar(Estadia estadia)
        {
            if (!Disponivel)
                throw new InvalidOperationException("O quarto " + numero + " já está ocupado.");
            estadiaAtual = estadia;
        }

        /// <summary>Libera o quarto e devolve a estadia que estava nele.</summary>
        public Estadia Liberar()
        {
            if (Disponivel)
                throw new InvalidOperationException("O quarto " + numero + " não está ocupado.");
            Estadia estadia = estadiaAtual;
            estadiaAtual = null;
            return estadia;
        }
    }
}
