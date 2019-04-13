package com.akb.springdatamongodb;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.akb.springdatamongodb.entity.Applicant;
import com.akb.springdatamongodb.entity.UserTO;
import com.akb.springdatamongodb.repository.ApplicantCustomRepo;
import com.akb.springdatamongodb.repository.ApplicantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataMongodbApplicationTests {
	
	@Autowired
	ApplicantRepository applicationRepo;
	
	@Autowired
	ApplicantCustomRepo applicationCustomRepo;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void getApplicant() {
		List<Applicant> appList = applicationRepo.findAll();
		Optional<Applicant> other = Optional.empty();
		Optional<Applicant> app = Optional.ofNullable(applicationRepo.findById(appList.get(0)
				.getId()).get());
		appList.get(0).setId(null);
		Optional<Applicant> appl = Optional.ofNullable(applicationRepo
				.findById(appList.get(0).getId())).orElse(other);
		System.out.println(appl.isPresent());
		assertThat(app.get().isPrimary(), is(true));
	}
	
	@Test
	public void getApplicantsById() {
		Pageable pageable = new PageRequest(1, 1); //new PageRequest(offset,limit)
		Page<Applicant> applicants = applicationCustomRepo
				.customSearch(Arrays.asList("QBIHQ75ZPN","0B5YQF6PHW"), pageable);
		applicants.get().forEach(x ->{
			System.out.println("Fetched SSN-"+x.getSsn());
		});
	}
	
	@Test
	public void getAllApplicantPaginated() throws ParseException {
		List<Applicant> appList = applicationCustomRepo.getAllApplicantPaginated(1, 1);
		System.out.println("Applicant Size " +appList.size());
		List<UserTO> appList1 = applicationRepo.getAllApplicantExcludeIncludeFields();
		System.out.println("Applicant  using @Query1 " +appList1);
		List<UserTO> appList2 = applicationRepo.getAllPrimaryApplicant();
		System.out.println("Applicant  using @Query2 " +appList2);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-dd-mm");
		Date date = sdf.parse("1991-01-01");
		List<UserTO> appList3 = applicationRepo.getEligibleApplicant(date);
		System.out.println("Applicant  using @Query3 " +appList3);
	}
	
	@Test
	public void getAllApplicantUsingRegex() {
		List<Applicant> appList = applicationCustomRepo.getAllApplicantUsingRegex();
		System.out.println("Applicant Size using regex " +appList.size());
		List<Applicant> appList1 = applicationRepo.getApplicantByFnameStartingWith("R");
		System.out.println("Fetched fname "+appList1.get(0).getFname());
		System.out.println("Applicant Size using regex1 " +appList1.size());
		List<Applicant> appList2 = applicationRepo.getApplicantByLnameEndingWith("r");
		System.out.println("Fetched fname "+appList2.get(0).getFname());
		System.out.println("Applicant Size using regex2 " +appList2.size());
		List<Applicant> appList3 = applicationRepo.getApplicantByLname("Dravid");
		System.out.println("Fetched fname "+appList3.get(0).getFname());
		System.out.println("Applicant Size using regex3 " +appList3.size());
	}

}
