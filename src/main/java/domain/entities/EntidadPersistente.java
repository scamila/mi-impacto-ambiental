package domain.entities;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public abstract class EntidadPersistente {
    @Id
    @GeneratedValue
    private Integer id;
}