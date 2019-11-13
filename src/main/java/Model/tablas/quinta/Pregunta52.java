package Model.tablas.quinta;

import Util.webservice.WSConsumer;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Pregunta52 implements Serializable {


    private List<JSONObject> objectList;
    private List<JSONObject> listElements;

    private String objectSelected;

    @PostConstruct
    public void init() {
        objectList = new ArrayList<JSONObject>();
        listElements= new ArrayList<JSONObject>();
        listElements.add(new JSONObject().put("label","V").put("command","-s "));
        listElements.add(new JSONObject().put("label","V").put("command","-s "));
        listElements.add(new JSONObject().put("label","V").put("command","-s "));
        listElements.add(new JSONObject().put("label","V").put("command","-s "));
        listElements.add(new JSONObject().put("label","V").put("command","-s "));


    }

    public void metodoNetflow() {
        objectList = new ArrayList<JSONObject>();
        System.out.println("metodoNetflow " + "http://localhost:8000/netflowStatics?command="+objectSelected);
        try {
            JSONArray jsonObject = new JSONArray(WSConsumer.get("http://localhost:8000/netflowStatics?command="+objectSelected));
            for (int x = 0; x < jsonObject.length(); x++) {
                objectList.add(jsonObject.getJSONObject(x));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sale metodoNetflow");

    }


    public List<JSONObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<JSONObject> objectList) {
        this.objectList = objectList;
    }

    public List<JSONObject> getListElements() {
        return listElements;
    }

    public void setListElements(List<JSONObject> listElements) {
        this.listElements = listElements;
    }

    public String getObjectSelected() {
        return objectSelected;
    }

    public void setObjectSelected(String objectSelected) {
        this.objectSelected = objectSelected;
    }
}
