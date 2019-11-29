package Model.tablas.cuarta;

import Constants.DataBaseC;
import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Eduardo Caballero Huesca
 *
 * Genera los templates a partir de los archivos de configuración
 */
@Named
@SessionScoped
public class Pregunta44 implements Serializable {

    private UploadedFile file;
    private List<Map> routerElements;
    private List<Map> listIPAddress;
    private MongoCollecction collection;
    private MongoCollecction collectionTemplates;
    private List<String> listRouters;
    private String router;
    private Boolean respuesta;

    @PostConstruct
    public void init() {
        collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_ROUTERS);
        collectionTemplates = MongoCollecction.getInstance(DataBaseC.COLLECTION_TEMPLATES);
    }

    public void obtenerRouters(){
        listRouters = new ArrayList<String>();
        DBCursor cur = collectionTemplates.getCollection().find();
        BasicDBList routers = new BasicDBList();
        while(cur.hasNext()) {
            listRouters.add(cur.next().get("router").toString());
        }
        System.out.println(routers);

    }


    public void generateTemplate(){
        routerElements = new ArrayList<Map>();
        listIPAddress = new ArrayList<Map>();
        BasicDBList interfaces = new BasicDBList();
        BasicDBList routes = new BasicDBList();
        BasicDBObject query = new BasicDBObject();
        DBCursor cur = collection.getCollection().find().sort(new BasicDBObject("router",-1)).limit(1);
        while(cur.hasNext()) {
            interfaces = (BasicDBList) (cur.next().get("interfaces"));
        }

        DBCursor cur2 = collection.getCollection().find().sort(new BasicDBObject("router",-1)).limit(1);
        while(cur2.hasNext()) {
            routes = (BasicDBList) (cur2.next().get("ip routes"));
        }

        for (int x =0 ; x < interfaces.size(); x++ ){
            BasicDBList list =   (BasicDBList)interfaces.get(x);
            Map<String, Object> mapToAdd = new HashMap<String,Object>();
            mapToAdd.put("interface",((BasicDBObject)list.get(0)).toMap().get("interface"));
            mapToAdd.put("ip address",((BasicDBObject)list.get(1)).toMap().get("ip address"));
            mapToAdd.put("shutdown","");
            if(list.size() == 3){
                mapToAdd.put("shutdown",((BasicDBObject)list.get(2)).toMap().get("shutdown"));
            }
            routerElements.add(mapToAdd);
        }

        for (int y =0 ; y < routes.size(); y++ ){
            BasicDBList list =   (BasicDBList)routes.get(y);
            Map<String, Object> mapToAdd = new HashMap<String,Object>();
            mapToAdd.put("ip route",((BasicDBObject)list.get(0)).toMap().get("ip route"));
            listIPAddress.add(mapToAdd);
        }
    }

    /**
     * Sube el archivo seleccionado, posteriormente consume el servicio REST
     * que genera el template en la BD.
     */
    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Aviso", file.getFileName() + " el template se generó correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            consumingRest("http://localhost:8000/template?archivo_conf="+file.getFileName(),Boolean.FALSE);

        }
        generateTemplate();
    }

    /**
     * Guarda el template en la colección de la BD
     */
    public void saveTemplate(){
        consumingRest("http://localhost:8000/insert",Boolean.FALSE);
        FacesMessage message = new FacesMessage("Aviso", "El template ha sido guardado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void obtenerTemplate(){
        routerElements = new ArrayList<Map>();
        listIPAddress = new ArrayList<Map>();
        BasicDBList interfaces = new BasicDBList();
        BasicDBList routes = new BasicDBList();
        BasicDBObject query = new BasicDBObject("router", router);


        DBObject cur = collectionTemplates.getCollection().findOne(query);
        interfaces = (BasicDBList) (cur.get("interfaces"));
        routes = (BasicDBList) (cur.get("ip routes"));


        for (int x =0 ; x < interfaces.size(); x++ ){
            BasicDBList list =   (BasicDBList)interfaces.get(x);
            Map<String, Object> mapToAdd = new HashMap<String,Object>();
            mapToAdd.put("interface",((BasicDBObject)list.get(0)).toMap().get("interface"));
            mapToAdd.put("ip address",((BasicDBObject)list.get(1)).toMap().get("ip address"));
            mapToAdd.put("shutdown","");
            if(list.size() == 3){
                mapToAdd.put("shutdown",((BasicDBObject)list.get(2)).toMap().get("shutdown"));
            }
            routerElements.add(mapToAdd);
        }

        for (int y =0 ; y < routes.size(); y++ ){
            BasicDBList list =   (BasicDBList)routes.get(y);
            Map<String, Object> mapToAdd = new HashMap<String,Object>();
            mapToAdd.put("ip route",((BasicDBObject)list.get(0)).toMap().get("ip route"));
            listIPAddress.add(mapToAdd);
        }
    }

    /**
     * Consume un servicio REST
     * @param rest
     */
    private void consumingRest(String rest, Boolean isRespuesta){
        try {
            URL url = new URL(rest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                if(isRespuesta){
                    respuesta = output.contains("true");
                }
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

    /**
     * Compara el archivo seleccionado con el template
     */
    public void uploadComparate() {
        if (file != null) {

            consumingRest("http://localhost:8000/template?archivo_conf="+file.getFileName(), Boolean.FALSE);
            compare();

        }

    }

    private void compare(){
        String routerWhitOutBlank = router.replace(" ", "%20");
        consumingRest("http://localhost:8000/comparate?routerIn=" + routerWhitOutBlank, Boolean.TRUE);
        if(respuesta){
            FacesMessage message = new FacesMessage("Aviso", "El archivo de configuración es el mismo que el template guardado");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
                FacesMessage message = new FacesMessage("Aviso", "El archivo de configuración es diferente que el template guardado");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }


    /**
     * Por algún problema no identificado se replica la palabra 'shutdown' en la lista
     * por lo tanto se tiene que usar este método para parcharlo
     * @param cadena
     * @return
     */
    public String evaluateShutdown(String cadena){
        if (cadena.equals("shutdown")){
            return "-";
        }else {
            return  cadena;
        }
    }



    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Aviso", file.getFileName() + " el template se generó correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Map> getRouterElements() {
        return routerElements;
    }

    public void setRouterElements(List<Map> routerElements) {
        this.routerElements = routerElements;
    }

    public List<Map> getListIPAddress() {
        return listIPAddress;
    }

    public void setListIPAddress(List<Map> listIPAddress) {
        this.listIPAddress = listIPAddress;
    }

    public List<String> getListRouters() {
        return listRouters;
    }

    public void setListRouters(List<String> listRouters) {
        this.listRouters = listRouters;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public Boolean getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Boolean respuesta) {
        this.respuesta = respuesta;
    }
}
