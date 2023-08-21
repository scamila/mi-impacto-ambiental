package sendmessage;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import static util.Constants.*;

public class WhatsAppConfig {

    private String senderNumber;

    public WhatsAppConfig(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public void send(String celular) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(celular),//celular
                        new com.twilio.type.PhoneNumber(this.senderNumber),
                        MESSAGE_TO_SEND)
                .create();
    }

}
