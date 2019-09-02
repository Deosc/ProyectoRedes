package Model.tablas.tercera;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import Util.mongo.MongoFinder;
import com.mongodb.DBObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class Pregunta32 implements Serializable {
    private List<Map> objectList;
    private Date inicialDate;
    private Date finalDate;

    @PostConstruct
    public void init() {
        onClick();
    }

    public void findList() {

        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_TRAPS);
            List<DBObject> listMongo = MongoFinder.findAll(collection);
            for (DBObject dbObject : listMongo) {
                objectList.add(dbObject.toMap());
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void onClick() {
        System.out.println("METODO");
        objectList = new ArrayList<Map>();
        findList();
    }

    public void onClick2() {
        System.out.println("METODO");
        objectList = new ArrayList<Map>();
        findList();
        objectList.clear();
    }

    public List<Map> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Map> objectList) {
        this.objectList = objectList;
    }

    public Date getInicialDate() {
        return inicialDate;
    }

    public void setInicialDate(Date inicialDate) {
        this.inicialDate = inicialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }
}
