package Util.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {

    public static final String ACCOUNT_SID = "ACe7b8a52b42714ac9d2d67f956c8eaf11";
    public static final String AUTH_TOKEN = "06f7862f595f4aae172296db8c16add7";

    public static String sendMessage(String number, String messageTxt) throws Exception {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            //Numero preferente: +525583178620
        Message message = Message.creator(new PhoneNumber(number),
                new PhoneNumber("+12562026522"),
                messageTxt).create();

        return message.getSid();

    }

}
