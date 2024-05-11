package bg.nbuteam4.myschool;

import bg.nbuteam4.myschool.entity.Role;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyschoolApplication.class, args);
	}

}
