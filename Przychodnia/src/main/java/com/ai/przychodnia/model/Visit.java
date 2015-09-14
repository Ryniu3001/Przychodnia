package com.ai.przychodnia.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Visit
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "visit_seq_gen")
	@SequenceGenerator(name = "visit_seq_gen", sequenceName = "Visit_ID_SEQ")
	private int id;

	@OneToOne
	@JoinColumn(name="patient") 
	private User patient;
	@OneToOne
	@JoinColumn(name="doctor") 
	private User doctor;

//	@NotNull
//	@DateTimeFormat(pattern = "H:mm")
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_S")
	private Date datee;
	
	@OneToOne
	private Clinic clinic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public Date getDatee() {
		return datee;
	}

	public void setDatee(Date date) {
		this.datee = date;
	}
	
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		result = prime * result + patient.hashCode();
//		return result;
//	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Visit))
			return false;
		Visit other = (Visit) obj;
		if (datee != other.getDatee()
				|| (patient.getId() != other.getPatient().getId())
				|| (doctor.getId() != other.getDoctor().getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", patient=" + patient.getPesel()
				+ ", doctor=" + doctor.getPwz() + ", date=" + datee + " ]";
	}
}
