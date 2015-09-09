package com.ai.przychodnia.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="User_data")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_seq_gen")
	@SequenceGenerator(name="user_seq_gen", sequenceName="USER_DATA_ID_SEQ")
	private int id;
	
	@Size(max=30)
	@NotEmpty
	@Column(name = "NAME")
	private String name;
	
	@Size(max=30)
	@NotEmpty
	@Column(name = "SURNAME", nullable = false)
	private String surname;
	
	@Size(max=11)
	@NotEmpty
	@Digits(integer=11, fraction=0)
	@Column(name = "PESEL", nullable = false, unique=true)
	private String pesel;
	
	@Size(max=15)
	@NotEmpty
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Size(max=6)
	@NotEmpty
	@Pattern(regexp="[0-9]{2}-[0-9]{3}", message="Wrong zip!")
	@Column(name = "ZIP_code", nullable = false)
	private String zip_code;
	
	@Size(max=4)
	@NotEmpty
	@Column(name = "HOUSE_NR", nullable = false)
	private String house_nr;
	
	/**
	 * <b>0 - pacjent <br/> 1 - lekarz <br/> 2 - admin</b>
	 */
	@Digits(integer=1, fraction=0)
	@Column(name = "TYPE", nullable = false)
	private int type;
	
	@Size(max=7)
	@Column(name = "PWZ", nullable = true)
	private String pwz;
	
	@Size(max=30)
	@NotEmpty
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	@NotEmpty
	private String password;
	
	@Column(name = "IS_ENABLED", nullable = false)
	private boolean is_enabled;
	
//	@OneToMany(mappedBy = "pk.doctor", cascade=CascadeType.ALL)
//	private Set<Doctor_Clinic> doctorsInClinic = new HashSet<Doctor_Clinic>(0);
	
	
	/* Setter and getters */
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getHouse_nr() {
		return house_nr;
	}

	public void setHouse_nr(String house_nr) {
		this.house_nr = house_nr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPwz() {
		return pwz;
	}

	public void setPwz(String pwz) {
		this.pwz = pwz;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(boolean is_enabled) {
		this.is_enabled = is_enabled;
	}
	

//	public Set<Doctor_Clinic> getDoctorsInClinic() {
//		return doctorsInClinic;
//	}
//
//	public void setDoctorsInClinic(Set<Doctor_Clinic> doctorsInClinic) {
//		this.doctorsInClinic = doctorsInClinic;
//	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + pesel.hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (pesel == null) {
            if (other.pesel != null)
                return false;
        } else if (!pesel.equals(other.pesel))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " + getPesel();
    }
}
