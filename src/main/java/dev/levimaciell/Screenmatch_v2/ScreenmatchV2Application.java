package dev.levimaciell.Screenmatch_v2;

import dev.levimaciell.Screenmatch_v2.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchV2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchV2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();
//
	}
}
