package com.javalearnings.securitydemo.entities.common;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;


/**
 * The persistent class for the ref_state database table.
 * 
 */
@Entity
@Table(name="ref_state")
@Data
public class RefState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="state_id")
	private int stateId;

	@Column(name="active_ind")
	private String activeInd;

	@Column(name="country_id")
	private int countryId;

	@Column(name="state_name")
	private String stateName;

}