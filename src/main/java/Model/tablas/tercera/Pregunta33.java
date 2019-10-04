package Model.tablas.tercera;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
     * @param ip dirección IP
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
