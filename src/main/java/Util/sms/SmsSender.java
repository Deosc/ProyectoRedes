package Util.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "AC07a4f3b9f1850ce457b0ad28788d85c0";
    public static final String AUTH_TOKEN = "098adb9cb87e3362610ae946d57327aa";

    public static String sendMessage(String number, String messageTxt) throws Exception {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            //Numero preferente: +525583178620
        Message message = Message.creator(new PhoneNumber(number),
                new PhoneNumber("+19852002433"),
                messageTxt).create();

        return message.getSid();

    }

}
