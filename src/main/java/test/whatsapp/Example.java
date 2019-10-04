package test.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Example {
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACa853a94dd3828a9060f96d2c8854b6eb";
    public static final String AUTH_TOKEN = "deebb66b97ccb88f50aede0e579b1c30";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+525583178620"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Hello there!")
                .create();

        System.out.println(message.getSid());
    }
}