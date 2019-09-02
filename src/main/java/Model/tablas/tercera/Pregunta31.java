package Model.tablas.tercera;

import Util.webservice.WSConsumer;
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
public class Pregunta31 implements Serializable {
    private List<JSONObject> listSuccess;
    private List<JSONObject> listError;

    private List<String> correos;
    private List<String> numeros;

    @PostConstruct
    public void init(){
        listSuccess = new ArrayList<JSONObject>();
        listError = new ArrayList<JSONObject>();
        correos = new ArrayList<String>();
        numeros = new ArrayList<String>();

        try {
            JSONArray jsonArray = new JSONArray(WSConsumer.get("https://jsonplaceholder.typicode.com/comments?postId=1"));
            for (int i = 0 ; i < jsonArray.length() ; i++){
                System.out.println();
                listSuccess.add(jsonArray.getJSONObject(i));
            }
            //System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(){
        System.out.println("Accion ping logger");
        System.out.println(correos);
        System.out.println(numeros);
    }

    public List<JSONObject> getListSuccess() {
        return listSuccess;
    }

    public void setListSuccess(List<JSONObject> listSuccess) {
        this.listSuccess = listSuccess;
    }

    public List<JSONObject> getListError() {
        return listError;
    }

    public void setListError(List<JSONObject> listError) {
        this.listError = listError;
    }

    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }

    public List<String> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<String> numeros) {
        this.numeros = numeros;
    }
}
