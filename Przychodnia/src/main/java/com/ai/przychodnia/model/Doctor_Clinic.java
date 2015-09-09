package com.ai.przychodnia.model;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "DOCTOR_CLINIC")
@AssociationOverrides({
		@AssociationOverride(name = "pk.doctor", joinColumns = @JoinColumn(name = "USER_ID")),
		@AssociationOverride(name = "pk.clinic", joinColumns = @JoinColumn(name = "CLINIC_ID")) })
public class Doctor_Clinic implements java.io.Serializable {
	
	private static final long serialVersionUID = 1502669526755884644L;

	@EmbeddedId
	private DoctorClinicId pk = new DoctorClinicId();
	

	private int dayOfWeek;
	
	@DateTimeFormat(pattern = "H:mm")
//	@Temporal(TemporalType.DATE)
	private Date hourFrom;
	

	@DateTimeFormat(pattern = "H:mm")
//	@Temporal(TemporalType.DATE)
	private Date hourTo;
	
	@DateTimeFormat(pattern = "H:mm")
//	@Temporal(TemporalType.DATE)
	private Date contract_Expire;


	@Transient
	public User getDoctor(){
		return getPk().getDoctor();
	}	

	public void setDoctor(User doctor){
		getPk().setDoctor(doctor);
	}
	
	@Transient
	public Clinic getClinic(){
		return getPk().getClinic();
	}
	
	public void setClinic(Clinic clinic){
		getPk().setClinic(clinic);
	}
	
	public DoctorClinicId getPk() {
		return pk;
	}
	public void setPk(DoctorClinicId pk) {
		this.pk = pk;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Date getHourFrom() {
		return hourFrom;
	}
	public void setHourFrom(Date hourFrom) {
		this.hourFrom = hourFrom;
	}
	public Date getHourTo() {
		return hourTo;
	}
	public void setHourTo(Date hourTo) {
		this.hourTo = hourTo;
	}
	public Date getContract_Expire() {
		return contract_Expire;
	}
	public void setContract_Expire(Date contract_Expire) {
		this.contract_Expire = contract_Expire;
	}
}
