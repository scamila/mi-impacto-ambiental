package domain.organizacion;

import sendmessage.EmailConfig;
import sendmessage.WhatsAppConfig;

public interface ANotificar {
    void serNotificadoPor(EmailConfig emailConfig, WhatsAppConfig whatsAppConfig);
}
