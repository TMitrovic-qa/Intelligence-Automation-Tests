package support;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Environment {

    private String baseUrl;
    private String username;
    private String password;
    private String apiUrl;
    private String fileServiceUrl;

    private static Environment instance = null;

    @Step("Setting up environment: {environment}")
    public void setupEnvironment(String environment) {
        if (environment.equals("DEV")) {
            baseUrl = "https://itest51.setvi.com/catalog?view=list";
            username = "singer@setvi.com";
            password = "ZDgSJqt37dt7";

        }

    }

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }
}
