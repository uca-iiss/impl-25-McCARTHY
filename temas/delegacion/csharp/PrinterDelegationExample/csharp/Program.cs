using System;

public interface IPrinter
{
    void Print(string content);
}

public class PdfPrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing PDF: " + content);
    }
}

public class WordPrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing Word Document: " + content);
    }
}

public class ImagePrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing Image: " + content);
    }
}

public class PrinterManager
{
    public delegate void PrintDelegate(string content);
    
    private PrintDelegate _printDelegate;

    public void SetPrinter(PrintDelegate printerMethod)
    {
        _printDelegate = printerMethod;
    }

    public void PrintDocument(string content)
    {
        if (_printDelegate != null)
        {
            _printDelegate(content);  // Aquí ocurre la delegación
        }
        else
        {
            Console.WriteLine("No printer configured.");
        }
    }
}

public class Program
{
    public static void Main(string[] args)
    {
        var manager = new PrinterManager();

        IPrinter pdfPrinter = new PdfPrinter();
        IPrinter wordPrinter = new WordPrinter();
        IPrinter imagePrinter = new ImagePrinter();

        // Delegar a PDF printer
        manager.SetPrinter(pdfPrinter.Print);
        manager.PrintDocument("Informe de ventas");

        // Delegar a Word printer
        manager.SetPrinter(wordPrinter.Print);
        manager.PrintDocument("Carta de presentación");

        //Delegamar a Imager printer 
        manager.SetPrinter(imagePrinter.Print);
        manager.PrintDocument("Diagrama arquitectónico");
    }
}
