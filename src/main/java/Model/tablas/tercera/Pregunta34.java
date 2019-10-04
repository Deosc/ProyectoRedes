package Model.tablas.tercera;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Pregunta34 implements Serializable {
    private String numero1;
    private Integer numero2;
    private String algo;

    private List<DBObject> lista;

    @PostConstruct
    public void init(){
        lista = new ArrayList<DBObject>();
    }
    public void metodo(){
        System.out.println(numero1);
        System.out.println(numero2);
        System.out.println(algo);

        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_PRUEBA);
            System.out.println("BasicDBObject example...");
            BasicDBObject document = new BasicDBObject();

            document.put("puto", numero1);
            document.put("1", numero2);
            document.put("2", algo);
            collection.insert(document);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<DBObject> getListt(){
        lista.clear();
        MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_PRUEBA);

        BasicDBObject query = new BasicDBObject();
        DBCursor cur = collection.getCollection().find();
        while(cur.hasNext()) {
            lista.add(cur.next());
        }
        return lista;
    }


    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public Integer getNumero2() {
        return numero2;
    }

    public void setNumero2(Integer numero2) {
        this.numero2 = numero2;
    }

    public String getAlgo() {
        return algo;
    }

    public void setAlgo(String algo) {
        this.algo = algo;
    }
}
