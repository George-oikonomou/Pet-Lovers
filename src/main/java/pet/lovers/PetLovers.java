package pet.lovers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PetLovers {

	public static void main(String[] args) {
		SpringApplication.run(PetLovers.class, args);
	}

}
