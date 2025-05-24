using NUnit.Framework;
using System;
using System.IO;

namespace PrinterTests
{
    [TestFixture]
    public class PrinterManagerTests
    {
        private StringWriter output;
        private PrinterManager manager;

        [SetUp]
        public void Setup()
        {
            output = new StringWriter();
            Console.SetOut(output);
            manager = new PrinterManager();
        }

        [TearDown]
        public void TearDown()
        {
            output.Dispose();
        }

        [Test]
        public void TestPdfPrinter()
        {
            IPrinter printer = new PdfPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Reporte anual");

            Assert.IsTrue(output.ToString().Contains("Printing PDF: Reporte anual"));
        }

        [Test]
        public void TestWordPrinter()
        {
            IPrinter printer = new WordPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Carta laboral");

            Assert.IsTrue(output.ToString().Contains("Printing Word Document: Carta laboral"));
        }

        [Test]
        public void TestImagePrinter()
        {
            IPrinter printer = new ImagePrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Logo corporativo");

            Assert.IsTrue(output.ToString().Contains("Printing Image: Logo corporativo"));
        }

        [Test]
        public void TestNoPrinterConfigured()
        {
            manager.PrintDocument("Sin impresora configurada");

            Assert.IsTrue(output.ToString().Contains("No printer configured."));
        }

        [Test]
        public void TestChangePrinterAtRuntime()
        {
            IPrinter pdfPrinter = new PdfPrinter();
            IPrinter wordPrinter = new WordPrinter();

            manager.SetPrinter(pdfPrinter.Print);
            manager.PrintDocument("Archivo PDF");

            manager.SetPrinter(wordPrinter.Print);
            manager.PrintDocument("Archivo Word");

            string result = output.ToString();
            Assert.IsTrue(result.Contains("Printing PDF: Archivo PDF"));
            Assert.IsTrue(result.Contains("Printing Word Document: Archivo Word"));
        }

        [Test]
        public void TestEmptyContentPrint()
        {
            IPrinter printer = new PdfPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("");

            Assert.IsTrue(output.ToString().Contains("Printing PDF: "));
        }
    }
}
