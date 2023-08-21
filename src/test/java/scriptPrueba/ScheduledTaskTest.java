package scriptPrueba;

import crontask.MessageTaskScheduler;
import domain.organizacion.Contacto;
import domain.organizacion.Organizacion;
import sendmessage.EmailConfig;
import sendmessage.WhatsAppConfig;
import org.junit.Test;
import org.quartz.*;

import java.util.ArrayList;
import java.util.List;

import static util.Constants.SENDER_EMAIL;
import static util.Constants.SENDER_NUMBER;

public class ScheduledTaskTest {

    @Test
    public void executeTask() throws SchedulerException, InterruptedException {
        EmailConfig emailConfig = new EmailConfig(SENDER_EMAIL);
        WhatsAppConfig whatsAppConfig = new WhatsAppConfig(SENDER_NUMBER);
        Organizacion organizacion = new Organizacion();
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(new Contacto("NAME", "EMAIL", "WHATSAPP"));
        organizacion.addContactos(contactos);
        MessageTaskScheduler quartz = new MessageTaskScheduler();
        quartz.setEmail(emailConfig);
        quartz.setWhatsAppMessage(whatsAppConfig);
        quartz.setOrganizacion(organizacion);
        quartz.fireJob();
    }

}
