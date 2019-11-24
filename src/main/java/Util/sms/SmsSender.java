package Util.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACfa32780abf9ecfaa1984edb59224eb6f";
    public static final String AUTH_TOKEN = "fbbbfa6f9317eb33bf587ef57a160c54";

    public static String sendMessage(String number, String messageTxt) throws Exception {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        //Numero preferente: +525583178620
        Message message = Message.creator(new PhoneNumber(number),
                new PhoneNumber("+12054305651"),
                messageTxt).create();

        return message.getSid();

    }

}
