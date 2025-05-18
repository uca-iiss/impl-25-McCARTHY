using NUnit.Framework;

[TestFixture]
public class AbstraccionTests {
    [Test]
    public void AreaDelCirculo_EsCorrecta() {
        var c = new Circulo(2);
        Assert.That(Math.Round(c.Area(), 2), Is.EqualTo(12.57).Within(0.01));
    }
}
