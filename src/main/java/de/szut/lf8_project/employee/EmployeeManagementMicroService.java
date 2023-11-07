package de.szut.lf8_project.employee;

import de.szut.lf8_project.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_project.security.AccessTokenDto;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;

@Service
public class EmployeeManagementMicroService {

    private final Environment environment;
    private final WebClient.Builder webClientBuilder;
    private final String authorizationToken;

    public EmployeeManagementMicroService(WebClient.Builder webClientBuilder, Environment environment) {
        this.webClientBuilder = webClientBuilder;
        this.environment = environment;
        this.authorizationToken = this.initAuthorization();
    }

    public String initAuthorization() {

        String grantType = environment.getProperty("employee.service.grant_type");
        String clientId = environment.getProperty("employee.service.client_id");
        String username = environment.getProperty("employee.service.username");
        String password = environment.getProperty("employee.service.password");

        return Objects.requireNonNull(webClientBuilder.build()
                .post()
                .uri("https://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData("grant_type", grantType)
                        .with("client_id", clientId)
                        .with("username", username)
                        .with("password", password))
                .retrieve()
                .bodyToMono(AccessTokenDto.class).block()).getAccess_token();
    }

    public GetEmployeeDto getEmployeeById(Long id) {
        String employeeUrl = "https://employee.szut.dev/employees/" + id;
        return webClientBuilder.build()
                .get()
                .uri(employeeUrl)
                .header("Authorization", "Bearer " + authorizationToken)
                .retrieve()
                .onStatus(
                        status -> status.equals(HttpStatus.NOT_FOUND),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new EmployeeNotFoundException(id)))
                )
                .bodyToMono(GetEmployeeDto.class).block();
    }
}