package Util.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class WhatsAppSenser {

    public static final String ACCOUNT_SID = "ACa853a94dd3828a9060f96d2c8854b6eb";
    public static final String AUTH_TOKEN = "deebb66b97ccb88f50aede0e579b1c30";

    public static String sendMessage(String number) throws Exception {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //Recomendado : +525583178620
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+number),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Error en la red!")
                .create();

        return message.getSid();
    }
}
