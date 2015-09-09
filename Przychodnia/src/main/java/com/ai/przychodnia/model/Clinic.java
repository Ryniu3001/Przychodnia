package com.ai.przychodnia.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="CLINIC")
public class Clinic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="clinic_seq_gen")
	@SequenceGenerator(name="clinic_seq_gen", sequenceName="Clinic_ID_SEQ")
	private int id;
	
	@NotEmpty
	@Size(max=30)
	private String name;
	
	@OneToMany(mappedBy = "pk.clinic", cascade=CascadeType.ALL)
	private Set<Doctor_Clinic> doctorsInClinic = new HashSet<Doctor_Clinic>(0);

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

	public Set<Doctor_Clinic> getDoctorsInClinic() {
		return doctorsInClinic;
	}

	public void setDoctorsInClinic(Set<Doctor_Clinic> doctorsInClinic) {
		this.doctorsInClinic = doctorsInClinic;
	}
	
//	@Override
//	public String toString(){
//		return this.getName();
//	}
		
}
