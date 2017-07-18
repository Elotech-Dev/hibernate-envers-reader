package br.com.elotech.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.elotech.hibernate.envers.EntityId;
import lombok.Data;

@Entity
@Data
public class City implements EntityId<Long> {

    @Id
    private Long id;

    private String descricao;

}
