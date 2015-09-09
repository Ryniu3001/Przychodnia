package com.ai.przychodnia.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class DoctorClinicId implements java.io.Serializable{

	private static final long serialVersionUID = -3706180007164830133L; 
	
	@ManyToOne
	private User doctor;
	
	@ManyToOne
	private Clinic clinic;
	
	
//	@JoinColumn(name="USER_ID")
	public User getDoctor() {
		return doctor;
	}
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	
	
//	@JoinColumn(name="CLINIC_ID")
	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		
		DoctorClinicId other = (DoctorClinicId) obj;
		if (doctor != null ? !doctor.equals(other.doctor) : other.doctor != null) return false;
        if (clinic != null ? !clinic.equals(other.clinic) : other.clinic != null) return false;
		return true;
	}
	
	@Override
    public int hashCode() {
        int result;
        result = (doctor != null ? doctor.hashCode() : 0);
        result = 31 * result + (clinic != null ? clinic.hashCode() : 0);
        return result;
    }

}
