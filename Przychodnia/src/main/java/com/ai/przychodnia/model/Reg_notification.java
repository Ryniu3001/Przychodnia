package com.ai.przychodnia.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="REG_NOTIFICATION")
public class Reg_notification
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="notify_seq_gen")
	@SequenceGenerator(name="notify_seq_gen", sequenceName="Reg_notification_ID_SEQ")
	private int id;
	
	private boolean read;
	
	@OneToOne
	@JoinColumn(name="USER_ID") 
	private User user_id;

	public Reg_notification(boolean read, User user_id)
	{
		this.read = read;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	
	
}
