package com.junapp.lms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="server")
public class Server implements PersistentEntity {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String name;
	
	@Column(name="log_path")
	private String logPath;
	
	@Column(name="log_name")
	private String logName;
	
	@SuppressWarnings("unused")
	private Server() { } // For Hibernate
	
	public Server(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}
}
