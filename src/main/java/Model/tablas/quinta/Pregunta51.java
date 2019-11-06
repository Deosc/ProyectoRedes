package Model.tablas.quinta;

import Constants.DataBaseC;
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
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class Pregunta51 implements Serializable {

    private List<Map> objectList;

    private String correos;
    private String numeros;

    @PostConstruct
    public void init() {
        objectList = new ArrayList<Map>();

    }

    public void metodoNetflow() {
        System.out.println("metodoNetflow");
        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_NETFLOW);
            List<DBObject> listMongo = MongoFinder.findAll(collection);
            for (DBObject dbObject : listMongo) {
                objectList.add(dbObject.toMap());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        System.out.println("sale metodoNetflow");

    }


    public List<Map> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Map> objectList) {
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
