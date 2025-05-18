public abstract class Figura {
    public abstract double Area();
}

public class Circulo : Figura {
    private double radio;
    public Circulo(double radio) => this.radio = radio;
    public override double Area() => Math.PI * radio * radio;
}

public class Program {
    public static void Main(string[] args) {
        Figura f = new Circulo(5);
        Console.WriteLine($"Área del círculo: {f.Area()}");
    }
}
