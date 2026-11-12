using System;

namespace HotelMotel.Dominio
{
    /// <summary>Estadia de hotel: além da entrada, guarda o cliente (nome e CPF).</summary>
    public class EstadiaHotel : Estadia
    {
        private readonly Cliente cliente;

        public EstadiaHotel(Cliente cliente, DateTime entrada) : base(entrada)
        {
            if (cliente == null)
                throw new ArgumentException("Hotel precisa dos dados do cliente.");
            this.cliente = cliente;
        }

        public Cliente Cliente { get { return cliente; } }
    }
}
