package br.com.elotech.hibernate.envers;

import java.io.Serializable;

public interface EntityId<ID extends Serializable> {

    ID getId();

}
