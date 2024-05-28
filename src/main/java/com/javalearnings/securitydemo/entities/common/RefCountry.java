package com.javalearnings.securitydemo.entities.common;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;


/**
 * The persistent class for the ref_country database table.
 * 
 */
@Entity
@Table(name="ref_country")
@Data
public class RefCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="country_id")
	private int countryId;

	@Column(name="active_ind")
	private String activeInd;

	@Column(name="country_abbr2")
	private String countryAbbr2;

	@Column(name="country_abbr3")
	private String countryAbbr3;

	@Column(name="country_name")
	private String countryName;

}
