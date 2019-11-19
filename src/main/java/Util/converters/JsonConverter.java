package Util.converters;

import org.primefaces.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@FacesConverter(value = "jsonConverter")
public class JsonConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConverter.class);


    public JsonConverter() {
    }


    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        System.out.println("entra " + entity.toString());
        return JSONObject.valueToString(entity);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        System.out.println(
                "gao " + new JSONObject(uuid)
        );
      return new JSONObject(uuid);
    }



}



