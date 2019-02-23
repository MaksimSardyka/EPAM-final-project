package com.epam.auction.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
	@Id
	@GeneratedValue
	private Long id;

	@Length(min = 3, max = 256)
	private String email;

	@Length(min = 1, max = 30)
	private String username;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//	@OrderBy("id")
//	private Set<Lot> lotList;
}
