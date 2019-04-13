package com.akb.springdatamongodb.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Document("applicant")
@AllArgsConstructor
public class Applicant {
	
	private String id;
	private String fname;
	private String lname;
	private Date dob;
	private String ssn;
	private String email;
	private boolean isPrimary;
	private List<Address> addressList;

}
