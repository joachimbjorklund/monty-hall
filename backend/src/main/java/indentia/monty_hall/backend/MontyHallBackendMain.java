package indentia.monty_hall.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MontyHallBackendMain {
	public static void main(String[] args) {
		SpringApplication.run(MontyHallBackendMain.class, args);
	}

	@Bean
    public MontyHallEngine montyHallEngine(){
	    return new MontyHallEngine();
    }
}