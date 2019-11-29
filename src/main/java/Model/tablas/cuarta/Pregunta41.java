package Model.tablas.cuarta;

import Util.webservice.WSConsumer;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Pregunta41 implements Serializable {


    private String ipSelected;
    private List<String> ips;

    @PostConstruct
    public void init(){
        ips = new ArrayList<String>();

        ips.add("10.0.1.1");
        ips.add("10.0.1.2");
        ips.add("10.0.2.1");
        ips.add("10.0.2.2");
        ips.add("10.0.3.1");
        ips.add("10.0.3.2");
        ips.add("10.0.4.1");
        ips.add("10.0.4.2");
        ips.add("10.0.5.1");
        ips.add("10.0.5.2");
        ips.add("10.0.6.1");
        ips.add("10.0.6.2");
        ips.add("10.0.7.1");
        ips.add("10.0.7.2");
        ips.add("10.0.8.1");
        ips.add("10.0.8.2");
        ips.add("10.0.9.1");
        ips.add("10.0.9.2");
        ips.add("10.0.10.1");
        ips.add("10.0.10.2");
        ips.add("10.0.11.1");
        ips.add("10.0.11.2");
        ips.add("10.0.12.1");
        ips.add("10.0.12.2");
        ips.add("10.0.13.1");
        ips.add("10.0.13.2");
        ips.add("10.0.14.1");
        ips.add("10.0.14.2");
        ips.add("10.0.15.1");
        ips.add("10.0.15.2");
        ips.add("10.0.16.1");
        ips.add("10.0.16.2");
        ips.add("10.0.17.1");
        ips.add("10.0.17.2");
        ips.add("10.0.18.1");
        ips.add("10.0.18.2");
        ips.add("10.0.19.1");
        ips.add("10.0.19.2");
        ips.add("10.0.20.1");
        ips.add("10.0.20.2");
        ips.add("10.0.21.1");
        ips.add("10.0.21.2");
        ips.add("10.0.22.1");
        ips.add("10.0.22.2");
        ips.add("10.0.23.1");
        ips.add("10.0.23.2");
        ips.add("10.0.24.1");
        ips.add("10.0.24.2");
        ips.add("10.0.25.1");
        ips.add("10.0.25.2");
        ips.add("10.0.26.1");
        ips.add("10.0.26.2");
        ips.add("10.0.27.1");
        ips.add("10.0.27.2");
    }

    public StreamedContent getFile() {
        try {
            File file = new File(consumeWS().getString("path"));
            InputStream targetStream = new FileInputStream(file);
            return new DefaultStreamedContent(targetStream, URLConnection.getFileNameMap().getContentTypeFor(file.getName()),ipSelected+".txt");
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultStreamedContent();

        }
    }


    public StreamedContent getFileIp(String ip) {
        ipSelected = ip;
        return getFile();
    }


    public JSONObject consumeWS() throws Exception{
        System.out.println("http://localhost:8000/backup?ipBackup="+ipSelected);
        return new JSONObject(WSConsumer.get("http://localhost:8000/backup?ipBackup="+ipSelected));

    }

    public String getIpSelected() {
        return ipSelected;
    }

    public void setIpSelected(String ipSelected) {
        this.ipSelected = ipSelected;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
