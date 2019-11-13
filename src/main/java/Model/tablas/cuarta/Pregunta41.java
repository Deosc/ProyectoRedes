package Model.tablas.cuarta;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;

@Named
@SessionScoped
public class Pregunta41 implements Serializable {


    private File file;

    public StreamedContent getFile() {
        try {
            File file = new File("/home/tfjfa/Descargas/2Icono (1).jpg");
            InputStream targetStream = new FileInputStream(file);
            return new DefaultStreamedContent(targetStream, URLConnection.getFileNameMap().getContentTypeFor(file.getName()),"file.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultStreamedContent();

        }
    }


}
