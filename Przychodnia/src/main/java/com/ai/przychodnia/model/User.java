package com.ai.przychodnia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;


@Entity
@Table(name="USER")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(max=30)
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Size(max=30)
	@Column(name = "SURNAME", nullable = false)
	private String surname;
	
	@Size(max=11)
	@Column(name = "PESEL", nullable = false, unique=true)
	//TODO czy wpisywac unique constaint?
	private String pesel;
	
	@Size(max=15)
	@Column(name = "CITY", nullable = false)
	private String city;
	
	@Size(max=30)
	@Column(name = "ZIP_code", nullable = false)
	private String zip_code;
	
	@Size(max=4)
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
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
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
        return "User [id=" + id + ", name=" + name + ", surname="
                + surname + ", type=" + type + " ]";
    }
}
