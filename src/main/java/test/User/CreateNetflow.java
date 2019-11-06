package test.User;

import Constants.DataBaseC;
import Constants.UserC;
import Util.mongo.MongoCollecction;
import com.mongodb.BasicDBObject;

import java.util.Random;

public class CreateNetflow {

    public static void main(String[] args) {
        try {
            MongoCollecction collection = MongoCollecction.getInstance(DataBaseC.COLLECTION_NETFLOW);
            System.out.println("BasicDBObject example...");

            for (int i = 0 ; i < 100 ; i++){

                BasicDBObject document = new BasicDBObject();

                document.put("source", "10.0.27.1");
                document.put("destiny", "10.0.26.1");
                document.put("duration", new Random().nextInt()%10);
                document.put("packets", new Random().nextInt()%100);
                document.put("bytes", new Random().nextInt()%100);
                document.put("flows", new Random().nextInt()%10);


                collection.insert(document);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
