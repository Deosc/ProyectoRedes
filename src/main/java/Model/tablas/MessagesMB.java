package Model.tablas;

import Util.mail.MailSender;
import Util.sms.SmsSender;
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
public class MessagesMB implements Serializable {
    private String messageMail;
    private String messageSms;
    private String correos;
    private String numeros;

    @PostConstruct
    public void init() {

    }

    public void onClick() {
        System.out.println(correos);
        System.out.println(messageMail);
        System.out.println(numeros);
        System.out.println(messageSms);
        try {
            if (!messageMail.isEmpty()) {
                MailSender.sendMessage(correos, messageMail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!messageSms.isEmpty()) {
                SmsSender.sendMessage(numeros,messageSms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getMessageMail() {
        return messageMail;
    }

    public void setMessageMail(String messageMail) {
        this.messageMail = messageMail;
    }

    public String getMessageSms() {
        return messageSms;
    }

    public void setMessageSms(String messageSms) {
        this.messageSms = messageSms;
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

    public void setMessages(String messageMail, String messageSms){
        setMessageMail(messageMail);
        setMessageSms(messageSms);

    }
}
