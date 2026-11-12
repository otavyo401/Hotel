using System;

namespace HotelMotel.Dominio
{
    /// <summary>Permanência de alguém em um quarto (do check-in até o check-out).</summary>
    public class Estadia
    {
        private readonly DateTime entrada;

        public Estadia(DateTime entrada)
        {
            this.entrada = entrada;
        }

        public DateTime Entrada { get { return entrada; } }
    }
}
