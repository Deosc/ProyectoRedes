package test.User;

import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBObject;
import Constants.DataBaseC;
import Constants.UserC;

public class CreateUser {

    public static void main(String[] args) {
        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_PRUEBA);
            System.out.println("BasicDBObject example...");
            BasicDBObject document = new BasicDBObject();

            document.put("puto", "hola2");
            document.put("1", "mundo2");
            document.put("2", "asaojsisio");
            collection.insert(document);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
