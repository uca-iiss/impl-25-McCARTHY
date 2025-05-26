using Microsoft.Extensions.DependencyInjection;
using System;

public interface IWeatherService
{
    double GetTemperature(string city);
}

public interface IAlertService
{
    void SendAlert(string message);
}

public interface IWeatherLogger
{
    void Log(string message);
}

public interface ICityWeatherMonitor
{
    void MonitorCity(string city);
}

public class FakeWeatherService : IWeatherService
{
    public double GetTemperature(string city)
    {
        return city.ToLower().Contains("desert") ? 42.0 : 21.5;
    }
}

public class EmailAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[EMAIL ALERT] {message}");
    }
}

public class PushAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[PUSH ALERT] {message}");
    }
}
 
 public class ConsoleWeatherLogger : IWeatherLogger
{
    public void Log(string message)
    {
        Console.WriteLine($"[LOG] {message}");
    }
}

public class CityWeatherMonitor : ICityWeatherMonitor
{
    private readonly IWeatherService _weatherService;
    private readonly IAlertService _alertService;
    private readonly IWeatherLogger _logger;

    public CityWeatherMonitor(IWeatherService weatherService, IAlertService alertService, IWeatherLogger logger)
    {
        _weatherService = weatherService;
        _alertService = alertService;
        _logger = logger;
    }

    public void MonitorCity(string city)
    {
        try
        {
            _logger.Log($"Consultando temperatura para {city}...");
            double temp = _weatherService.GetTemperature(city);
            _logger.Log($"Temperatura en {city}: {temp}°C");

            if (temp > 35)
            {
                _alertService.SendAlert($"¡Alerta de calor en {city}! Temperatura: {temp}°C");
            }

            _logger.Log($"Monitoreo completado para {city}.");
        }
        catch (Exception ex)
        {
            _logger.Log($"Error al monitorear {city}: {ex.Message}");
            throw;
        }
    }
}


public class Program
{
    public static void Main(string[] args)
    {
        var services = new ServiceCollection();
        ConfigureServices(services, args);

        var provider = services.BuildServiceProvider();
        var monitor = provider.GetService<ICityWeatherMonitor>();
        monitor?.MonitorCity("Desert City");
    }

    private static void ConfigureServices(IServiceCollection services, string[] args)
    {
        services.AddSingleton<IWeatherService, FakeWeatherService>();
        services.AddSingleton<IWeatherLogger, ConsoleWeatherLogger>();

        if (args.Length > 0 && args[0].ToLower() == "push")
        {
            services.AddTransient<IAlertService, PushAlertService>();
        }
        else
        {
            services.AddTransient<IAlertService, EmailAlertService>();
        }

        services.AddTransient<ICityWeatherMonitor, CityWeatherMonitor>();
    }
}
