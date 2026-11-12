using System;
using System.Globalization;

namespace HotelMotel.Dominio
{
    /// <summary>Formatação no padrão brasileiro sem depender da cultura do navegador.</summary>
    public static class Formato
    {
        /// <summary>1234.5 -> "1.234,50"</summary>
        public static string Dinheiro(decimal valor)
        {
            string invariante = valor.ToString("N2", CultureInfo.InvariantCulture); // 1,234.50
            return invariante.Replace(",", "#").Replace(".", ",").Replace("#", ".");
        }

        public static string DataHora(DateTime data)
        {
            return data.ToString("dd/MM/yyyy HH:mm", CultureInfo.InvariantCulture);
        }
    }
}
