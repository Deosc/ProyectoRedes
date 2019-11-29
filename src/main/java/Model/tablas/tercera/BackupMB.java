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
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
@SessionScoped
public class BackupMB implements Serializable {

    private String ipSelected;
    private UploadedFile uploadedFile; // +getter+setter


    @PostConstruct
    public void init() {

    }

    public void upload() {
        try {
            InputStream inputStream;
            inputStream = uploadedFile.getInputstream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            File file = new File("/tftpboot/"+ipSelected+"_bckp.txt");
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(buffer);


            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_READ);
            perms.add(PosixFilePermission.OTHERS_WRITE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);

            Files.setPosixFilePermissions(file.toPath(), perms);


            String respuesta = WSConsumer.get("http://localhost:8000/restoreConfig?ipRestore="+ipSelected);

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
