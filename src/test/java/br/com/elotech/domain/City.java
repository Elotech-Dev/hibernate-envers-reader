package br.com.elotech.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

import br.com.elotech.hibernate.envers.RevisionEntity;
import lombok.Data;

@Entity
@Data
@Audited
public class City implements RevisionEntity<Long> {

    @Id
    private Long id;

    private String descricao;

}
