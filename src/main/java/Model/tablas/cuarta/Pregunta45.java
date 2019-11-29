package Model.tablas.cuarta;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

@Named
@SessionScoped
public class Pregunta45 implements Serializable {

    private UploadedFile file;
    private Pregunta44 pregunta44;


    @PostConstruct
    public void init() {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Pregunta44 nB =(Pregunta44) session.getAttribute("router");
        System.out.println(nB.getRouter());
    }







    /**
     * Consume un servicio REST
     * @param rest
     */


    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Aviso", file.getFileName() + " el template se generó correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
