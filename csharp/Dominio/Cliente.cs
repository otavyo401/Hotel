using System;

namespace HotelMotel.Dominio
{
    /// <summary>Cliente do hotel (o motel não precisa identificar o cliente).</summary>
    public class Cliente
    {
        private readonly string nome;
        private readonly string cpf;

        public Cliente(string nome, string cpf)
        {
            if (string.IsNullOrWhiteSpace(nome))
                throw new ArgumentException("Nome do cliente é obrigatório.");
            if (string.IsNullOrWhiteSpace(cpf))
                throw new ArgumentException("CPF do cliente é obrigatório.");
            this.nome = nome.Trim();
            this.cpf = cpf.Trim();
        }

        public string Nome { get { return nome; } }
        public string Cpf { get { return cpf; } }

        public override string ToString()
        {
            return nome + " (CPF " + cpf + ")";
        }
    }
}
