package com.epam.auction.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
	@Id
	@GeneratedValue
	Long id;

	@Length(min = 0, max = 40)
	String name;
	
	@Length(min = 0, max = 256)
	String description;
//	
//	@OneToOne
//	User user;
}
