package Model.tablas.tercera;

import Model.general.MenuMB;
import Util.mail.MailSender;
import Util.sms.SmsSender;
import Util.webservice.WSConsumer;
import Util.whatsapp.WhatsAppSenser;
import com.google.gson.JsonObject;
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
    private List<String> listSuccess;
    private List<String> listError;
    private List<String> listMessages;

    private String correos;
    private String numeros;

    @PostConstruct
    public void init() {
        listSuccess = new ArrayList<String>();
        listError = new ArrayList<String>();

    }

    public String metodoPing(){
        listError = new ArrayList<String>();
        listSuccess = new ArrayList<String>();
        try {
            System.out.println("Entra");
            String respuesta = WSConsumer.get("http://localhost:8000/ping?ipRoot=10.0.1.1");
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray jsonArray = jsonObject.getJSONArray("reachable");
            for (int i = 0; i < jsonArray.length(); i++) {
                listSuccess.add(jsonArray.getString(i));
            }

            JSONArray jsonArray2 = jsonObject.getJSONArray("unreachable");
            for (int i = 0; i < jsonArray2.length(); i++) {
                listError.add(jsonArray2.getString(i));
            }
            //System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MenuMB menuMB = new MenuMB();
        return menuMB.redirectPingLogger();
    }

    public void metodoList(String ip){
        listMessages = new ArrayList<>();
        try {
            System.out.println("Entra");
            String respuesta = WSConsumer.  get("http://localhost:8000/changes?ipChanges="+ip);
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray jsonArray = jsonObject.getJSONArray("message");
            for (int i = 0; i < jsonArray.length(); i++) {
                listMessages.add(jsonArray.getString(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void refesh(){
        metodoPing();
    }

    public String generateMessage(){
        StringBuilder stringBuilder = new StringBuilder();
        if(listError.isEmpty()){
            stringBuilder.append("La red esta encendida, sin errores.");
        }else {
            stringBuilder.append("Existen redes apagadas: ");
            for (String ip : listError){
                stringBuilder.append(" "+ ip);
            }
        }

        return stringBuilder.toString();
    }


    public List<String> getListSuccess() {
        return listSuccess;
    }

    public void setListSuccess(List<String> listSuccess) {
        this.listSuccess = listSuccess;
    }

    public List<String> getListError() {
        return listError;
    }

    public void setListError(List<String> listError) {
        this.listError = listError;
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

    public List<String> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<String> listMessages) {
        this.listMessages = listMessages;
    }
}
