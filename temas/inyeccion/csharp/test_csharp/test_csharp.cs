using Moq;
using NUnit.Framework;
using System;

namespace Tests_Clima
{
    public class WeatherMonitorTests
    {
        private Mock<IWeatherService> mockWeatherService;
        private Mock<IAlertService> mockAlertService;
        private Mock<IWeatherLogger> mockLogger;
        private CityWeatherMonitor monitor;

        [SetUp]
        public void Setup()
        {
            mockWeatherService = new Mock<IWeatherService>();
            mockAlertService = new Mock<IAlertService>();
            mockLogger = new Mock<IWeatherLogger>();

            monitor = new CityWeatherMonitor(mockWeatherService.Object, mockAlertService.Object, mockLogger.Object);

            mockLogger.Setup(l => l.Log(It.IsAny<string>()))
                      .Callback<string>(msg => Console.WriteLine($"[MockLog] {msg}"));
        }

        [Test]
        public void MonitorCity_CallsGetTemperature()
        {
            string city = "TestCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(25.0);

            monitor.MonitorCity(city);

            mockWeatherService.Verify(w => w.GetTemperature(city), Times.Once);
        }

        [Test]
        public void MonitorCity_LogsAllMessages()
        {
            string city = "LogCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(20.0);

            monitor.MonitorCity(city);

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Consultando temperatura"))), Times.Once);
            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Monitoreo completado"))), Times.Once);
        }

        [Test]
        public void MonitorCity_SendsHeatAlert_WhenTemperatureHigh()
        {
            string city = "HotCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(40.0);

            monitor.MonitorCity(city);

            mockAlertService.Verify(a => a.SendAlert(It.Is<string>(s => s.Contains("¡Alerta de calor"))), Times.Once);
        }

        [Test]
        public void MonitorCity_WhenAlertServiceFails_LogsError()
        {
            string city = "FailCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(50.0);
            mockAlertService.Setup(a => a.SendAlert(It.IsAny<string>())).Throws(new Exception("Error en alerta"));

            Assert.Throws<Exception>(() => monitor.MonitorCity(city));

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Error al monitorear"))), Times.Once);
        }
    }
}
