package Model.general;


import Util.mongo.MongoCollecction;
import Util.mongo.MongoFinder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import Constants.DataBaseC;
import Constants.UserC;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginMB implements Serializable {

    private static final String ON_LOGIN_ERROR = "";
    private static final String ON_LOGIN_OK = "/content/main.xhtml";

    private String user;
    private String password;
    private Integer tipo;
    private DBObject userData;

    @PostConstruct
    public void init() {
        System.out.println("Init");
    }

    public String login() {

        String redirect = ON_LOGIN_ERROR;
        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_USER);

            //Create object
            BasicDBObject document = new BasicDBObject();
            document.put(UserC.USER, getUser());
            document.put(UserC.PASS, getPassword());

            //Find
            DBCursor cursor = MongoFinder.find(collection, document);

            //Results
            while (cursor.hasNext()) {
                userData = cursor.next();
                tipo = (Integer)userData.get(UserC.TYPE);
                redirect = ON_LOGIN_OK;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(">> " + userData);
        System.out.println(redirect);
        return redirect;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Boolean isAdmin(){
        return tipo.equals(1);
    }
    public Boolean isUser(){
        return tipo.equals(2);
    }
}
