package domain.organizacion;

import domain.entities.EntidadPersistente;
import sendmessage.EmailConfig;
import sendmessage.WhatsAppConfig;

import javax.persistence.*;

@Entity
@Table(name = "contacto")
public class Contacto extends EntidadPersistente implements ANotificar {

    @Column
    private String nombre;
    @Column
    private String email;
    @Column
    private String celular;

    public Contacto(String nombre, String email, String celular) {
        this.nombre = nombre;
        this.email = email;
        this.celular = celular;
    }

    @Override
    public void serNotificadoPor(EmailConfig emailConfig, WhatsAppConfig whatsAppConfig) {
        emailConfig.send(this.email);
        whatsAppConfig.send(this.celular);
    }
}
