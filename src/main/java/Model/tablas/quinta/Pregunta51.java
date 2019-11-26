package Model.tablas.quinta;

import Constants.DataBaseC;
import Model.general.MenuMB;
import Util.mail.MailSender;
import Util.mongo.MongoCollecction;
import Util.mongo.MongoFinder;
import Util.sms.SmsSender;
import Util.webservice.WSConsumer;
import Util.whatsapp.WhatsAppSenser;
import com.mongodb.DBObject;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class Pregunta51 implements Serializable {

    private List<JSONObject> objectList;

    private String correos;
    private String numeros;

    @PostConstruct
    public void init() {
        objectList = new ArrayList<JSONObject>();

    }

    public void metodoNetflow() {
        objectList = new ArrayList<JSONObject>();
        System.out.println("metodoNetflow");
        try {
            JSONArray jsonObject = new JSONArray(WSConsumer.get("http://localhost:8000/netflowScan"));
            for (int x = 0; x < jsonObject.length(); x++) {
                objectList.add(jsonObject.getJSONObject(x));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sale metodoNetflow");

    }

    public String redirect(){
        metodoNetflow();
        MenuMB menuMB = new MenuMB();
        return menuMB.redirectTraps();
    }


    public List<JSONObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<JSONObject> objectList) {
        this.objectList = objectList;
    }

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }


}
