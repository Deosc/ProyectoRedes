package Model.tablas.tercera;

import Model.general.MenuMB;
import Util.webservice.WSConsumer;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BackupMB implements Serializable {

    private String ipSelected;
    private UploadedFile uploadedFile; // +getter+setter


    @PostConstruct
    public void init() {

    }

    public void upload() {
        String fileName = uploadedFile.getFileName();
        String contentType = uploadedFile.getContentType();
        try {
            InputStream inputStream;
            inputStream = uploadedFile.getInputstream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            File file = new File("/tmp/"+ipSelected+"toRestore.txt");
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getIpSelected() {
        return ipSelected;
    }

    public void setIpSelected(String ipSelected) {
        this.ipSelected = ipSelected;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
