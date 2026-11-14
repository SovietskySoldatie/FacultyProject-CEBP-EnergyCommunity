package EnergyCommunity.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Core Energy Service API",
                version = "1.0",
                description = "Single source of truth for managing users, smart houses, batteries, and solar panels in the Energy Community."
        )
)
public class CoreEnergyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreEnergyApplication.class, args);
    }
}
