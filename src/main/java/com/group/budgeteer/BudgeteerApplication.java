package com.group.budgeteer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing so that Entity Listener in ApplicationEntity class will work
@EnableJpaAuditing
public class BudgeteerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgeteerApplication.class, args);
	}

}
