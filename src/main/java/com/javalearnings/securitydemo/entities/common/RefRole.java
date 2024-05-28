package com.javalearnings.securitydemo.entities.common;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;


/**
 * The persistent class for the ref_role database table.
 * 
 */
@Entity
@Table(name="ref_role")
@Data
public class RefRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer roleId;

	@Column(name="active_ind")
	private String activeInd;

	@Column(name="role_level")
	private Integer roleLevel;

	@Column(name="role_name")
	private String roleName;

}