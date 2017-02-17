package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Room buildMud() {
        Room e = new Room();
        Room w = new Room();

        e.setWest(w);
        w.setEast(e);

        return e;
    }
}
