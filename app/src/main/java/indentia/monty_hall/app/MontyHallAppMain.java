package indentia.monty_hall.app;

import indentia.monty_hall.backend.MontyHallBackendMain;
import indentia.monty_hall.frontend.MontyHallFrontendMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MontyHallFrontendMain.class, MontyHallBackendMain.class})
public class MontyHallAppMain {
	public static void main(String[] args) {
		SpringApplication.run(MontyHallAppMain.class, args);
	}
}