package Model.tablas.tercera;

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

    private String correos;
    private String numeros;

    @PostConstruct
    public void init() {
        listSuccess = new ArrayList<String>();
        listError = new ArrayList<String>();

        try {
            System.out.println("Entra");
            String respuesta = WSConsumer.get("http://localhost:5000/10.0.1.2");
            JSONObject jsonObject = new JSONObject(respuesta);
            JSONArray jsonArray = jsonObject.getJSONArray("reachable");
            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println();
                listSuccess.add(jsonArray.getString(i));
            }

            JSONArray jsonArray2 = jsonObject.getJSONArray("unreachable");
            for (int i = 0; i < jsonArray2.length(); i++) {
                System.out.println();
                listSuccess.add(jsonArray2.getString(i));
            }
            //System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick() {
        System.out.println("Accion ping logger");
        System.out.println(correos);
        System.out.println(numeros);
        try {
            if (!listError.isEmpty()) {
                MailSender.sendMessage(correos);
                SmsSender.sendMessage(numeros);
                WhatsAppSenser.sendMessage(numeros);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
