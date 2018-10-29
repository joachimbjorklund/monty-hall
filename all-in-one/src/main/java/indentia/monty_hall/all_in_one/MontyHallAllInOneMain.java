package indentia.monty_hall.all_in_one;

import indentia.monty_hall.backend.MontyHallBackendMain;
import indentia.monty_hall.frontend.MontyHallFrontendMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MontyHallFrontendMain.class, MontyHallBackendMain.class})
public class MontyHallAllInOneMain {
	public static void main(String[] args) {
		SpringApplication.run(MontyHallAllInOneMain.class, args);
	}
}