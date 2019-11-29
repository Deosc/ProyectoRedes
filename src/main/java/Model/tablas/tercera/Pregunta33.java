package Model.tablas.tercera;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Bean que controla la pantalla correspondiente al
 * servidor syslog
 * @author Carlos Eduardo Caballero Huesca
 */
@Named
@SessionScoped
public class Pregunta33 implements Serializable {

    private MongoCollecction collection;
    private List<String> listaIP;
    private List<Integer> listNiveles;
    private List<Map> listSyslogs;
    private String ip;
    private Integer nivel;

    @PostConstruct
    public void init() {
        collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_SYSLOGS);
       listaIP = new ArrayList<String>();
       listNiveles = new ArrayList<Integer>();
       listSyslogs = new ArrayList<Map>();
       obtenerIPs();
    }

    /**
     * Obtiene las direcciones IP de la carpeta donde se encuentran almacenados
     * los archivos syslog, para posteriormente agregarlos a su respectiva lista
     */
    private void obtenerIPs(){
        for (Object dbObject : collection.getCollection().distinct("ip")) {
            listaIP.add(dbObject.toString());
        }
    }

    /**
     * Obtiene los syslogs correspondientes a la IP
     */
    public void obtenerSyslogs(){
        listSyslogs.clear();
        BasicDBObject query = new BasicDBObject();
        query.put("ip", ip);
        DBCursor cur = collection.getCollection().find(query);
        while(cur.hasNext()) {
            listSyslogs.add(cur.next().toMap());
        }
    }




    /**
     * Consume el API REST que filtra y crea el archivo con la información del router
     */
    public void downloadInfo(){
        consumingRest("http://localhost:8000/telnet?host="+ip);
        FacesMessage message = new FacesMessage("Aviso", "El archivo " + ip +".txt"+" se ha descargado. Revisa tu unidad local");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    /**
     * Consume un servicio REST
     * @param rest
     */
    private void consumingRest(String rest){
        try {
            URL url = new URL(rest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {

                System.out.println(output.contains("true"));

            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }


    public List<String> getListaIP() {
        return listaIP;
    }

    public void setListaIP(List<String> listaIP) {
        this.listaIP = listaIP;
    }

    public List<Integer> getListNiveles() {
        return listNiveles;
    }

    public void setListNiveles(List<Integer> listNiveles) {
        this.listNiveles = listNiveles;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public List<Map> getListSyslogs() {
        return listSyslogs;
    }

    public void setListSyslogs(List<Map> listSyslogs) {
        this.listSyslogs = listSyslogs;
    }
}
