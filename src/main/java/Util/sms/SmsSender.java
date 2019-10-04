package Util.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACa853a94dd3828a9060f96d2c8854b6eb";
    public static final String AUTH_TOKEN = "deebb66b97ccb88f50aede0e579b1c30";

    public static String sendMessage(String number) throws Exception {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //Numero preferente: +525583178620
        Message message = Message.creator(new PhoneNumber(number),
                new PhoneNumber("+12563803894"),
                "La red cuenta con elementos apagados.").create();

        return message.getSid();

    }

}
