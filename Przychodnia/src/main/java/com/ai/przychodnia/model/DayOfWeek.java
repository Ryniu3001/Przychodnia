package com.ai.przychodnia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class DayOfWeek
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="dayofweek_seq_gen")
	@SequenceGenerator(name="dayofweek_seq_gen", sequenceName="DAYOFWEEK_ID_SEQ")
	private int id;
	
	@NotEmpty
	@Size(max=20)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
