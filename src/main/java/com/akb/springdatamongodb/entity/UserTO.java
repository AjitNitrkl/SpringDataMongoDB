package com.akb.springdatamongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserTO {
	private String fname;
	private String ssn;

}
