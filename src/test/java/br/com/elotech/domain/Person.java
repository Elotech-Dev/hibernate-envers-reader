package br.com.elotech.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.envers.Audited;

import br.com.elotech.hibernate.envers.EntityId;
import lombok.Data;

@Data
@Entity
@Audited
public class Person implements EntityId<Long> {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "seq_person")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person")
    private Long id;

    private String name;

}
