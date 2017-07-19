package br.com.elotech.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import br.com.elotech.hibernate.envers.RevisionEntity;
import lombok.Data;

@Data
@Entity
@Audited
public class Person implements RevisionEntity<Long> {

	@Id
	private Long id;

	private String name;

	@ManyToOne
	private City city;

}
