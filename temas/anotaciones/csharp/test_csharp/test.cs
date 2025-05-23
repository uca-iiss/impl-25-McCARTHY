using NUnit.Framework;
using System;
using System.Text.Json;
using System.Collections.Generic;


namespace EmpresaTests
{
    [TestFixture]
    public class TrabajadorTests
    {
        [Test]
        public void ValidarSalario_Valido_NoLanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Pedro", Salario = 3000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void ValidarSalario_Invalido_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Laura", Salario = 200m };
            var ex = Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
            StringAssert.Contains("Salario", ex.Message);
        }

        [Test]
        public void SerializarEmpresa_ContieneDatosCorrectos()
        {
            var empresa = new Empresa
            {
                Nombre = "Innovatech",
                Ubicacion = "Barcelona",
                Empleados = new List<Trabajador>
                {
                    new Trabajador { Nombre = "Carlos", Salario = 4000m },
                    new Trabajador { Nombre = "Elena", Salario = 5200m }
                }
            };

            string json = JsonSerializer.Serialize(empresa);

            StringAssert.Contains("Innovatech", json);
            StringAssert.Contains("Barcelona", json);
            StringAssert.Contains("Carlos", json);
            StringAssert.Contains("Elena", json);
        }

        [Test]
        public void Salario_MinimoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Pepe", Salario = 1000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void Salario_MaximoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Lucía", Salario = 10000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void Salario_JustoPorDebajoDelMinimo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Mario", Salario = 999.99m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }

        [Test]
        public void Salario_JustoPorEncimaDelMaximo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Ana", Salario = 10000.01m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }

        [Test]
        public void SerializacionEmpresa_PropiedadesJsonRenombradasCorrectamente()
        {
            var empresa = new Empresa
            {
                Nombre = "DataCorp",
                Ubicacion = "Valencia",
                Empleados = new List<Trabajador>()
            };

            string json = JsonSerializer.Serialize(empresa);
            StringAssert.Contains("\"nombre_empresa\"", json);
            StringAssert.Contains("\"ubicacion\"", json);
            StringAssert.Contains("\"trabajadores\"", json);
        }
    }
}