package jpabook.jpashop;

import jpabook.jpashop.domains.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);

		Member member = new Member();
		member.setName("Test");

		String name = member.getName();

		System.out.println("name = " + name);

	}

}
