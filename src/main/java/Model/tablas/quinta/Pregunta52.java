package Model.tablas.quinta;

import Util.webservice.WSConsumer;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
    private List<JSONObject> listElementsFilter;

    private String objectSelected;
    private String objectSelectedFilter;


    private BarChartModel barModel;


    @PostConstruct
    public void init() {
        objectList = new ArrayList<JSONObject>();
        listElements= new ArrayList<JSONObject>();
        listElementsFilter= new ArrayList<JSONObject>();
        listElements.add(new JSONObject().put("label","Flows").put("command","flows").put("propertie","flows"));
        listElements.add(new JSONObject().put("label","Paquetes").put("command","packets").put("propertie","paquetes"));
        listElements.add(new JSONObject().put("label","Bytes").put("command","bytes").put("propertie","bytes"));
        listElements.add(new JSONObject().put("label","Paquetes por segundo").put("command","pps").put("propertie","pps"));
        listElements.add(new JSONObject().put("label","Bites por segundo").put("command","bps").put("propertie","bps"));
        listElements.add(new JSONObject().put("label","Bytes por paquete").put("command","bpp").put("propertie","bpp"));


        listElementsFilter.add(new JSONObject().put("label","Fuente").put("command","-s%20srcip/"));
        listElementsFilter.add(new JSONObject().put("label","Destino").put("command","-s%20dstip/"));
        listElementsFilter.add(new JSONObject().put("label","IP").put("command","-s%20ip/"));
        listElementsFilter.add(new JSONObject().put("label","Puerto fuente").put("command","-s%20srcport/"));
        listElementsFilter.add(new JSONObject().put("label","Puerto destino").put("command","-s%20dstport/"));
        listElementsFilter.add(new JSONObject().put("label","Puerto").put("command","-s%20port/"));
        listElementsFilter.add(new JSONObject().put("label","Interfas fuente").put("command","-s%20inif/"));
        listElementsFilter.add(new JSONObject().put("label","Interfas destino").put("command","-s%20outif/"));
        listElementsFilter.add(new JSONObject().put("label","Cualquier interfas").put("command","-s%20if/"));



    }

    public void metodoNetflow() {
        objectList = new ArrayList<JSONObject>();
        System.out.println("metodoNetflow " + "http://localhost:8000/netflowStatics?command="+objectSelectedFilter+objectSelected);
        try {
            JSONArray jsonObject = new JSONArray(WSConsumer.get("http://localhost:8000/netflowStatics?command="+objectSelectedFilter+objectSelected));
            for (int x = 0; x < jsonObject.length(); x++) {
                objectList.add(jsonObject.getJSONObject(x));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sale metodoNetflow");
        createBarModels();

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

    public List<JSONObject> getListElementsFilter() {
        return listElementsFilter;
    }

    public void setListElementsFilter(List<JSONObject> listElementsFilter) {
        this.listElementsFilter = listElementsFilter;
    }

    public String getObjectSelectedFilter() {
        return objectSelectedFilter;
    }

    public void setObjectSelectedFilter(String objectSelectedFilter) {
        this.objectSelectedFilter = objectSelectedFilter;
    }



    private BarChartModel initBarModel(JSONObject filter, JSONObject order) {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel(order.getString("label"));
        for (JSONObject jsonObject : objectList){
            boys.set(jsonObject.getString("src"),Long.parseLong(jsonObject.getString(filter.getString("propertie")).split("[(]")[0]));
        }


        model.addSeries(boys);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        JSONObject jsonObjectFilter = findInList(listElements,objectSelected);
        JSONObject jsonObjectOrder = findInList(listElementsFilter,objectSelectedFilter);
        barModel = initBarModel(jsonObjectFilter, jsonObjectOrder);

        barModel.setTitle("Mejores 10 ");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(jsonObjectFilter.getString("label"));


        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel(jsonObjectOrder.getString("label"));
        yAxis.setMin(0);
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    private JSONObject findInList(List<JSONObject> list, String command){
        for (JSONObject jsonObject : list){
            if (jsonObject.getString("command").equals(command)){
                return jsonObject;
            }
        }
        return new JSONObject();
    }
}
