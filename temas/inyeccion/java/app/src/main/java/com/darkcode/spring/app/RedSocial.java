package com.darkcode.spring.app;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RedSocial 
{
    private final List<Foto> fotos;

    public RedSocial(List<Foto> fotos) 
    {
        this.fotos = fotos;
    }

    public String mostrarFotos() 
    {
        StringBuilder sb = new StringBuilder();
        for (Foto foto: fotos) {
            sb.append(foto.getNombre())
              .append("<br> |--------|<br>")
              .append("\0\0\0\0"+foto.getImagen()+"\0\0\0\0<br>")
              .append("|--------|<br><br>");
        }
        return sb.toString();
    }
}