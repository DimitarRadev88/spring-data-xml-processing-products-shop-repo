package bg.softuni.springdataxmlprocessing.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {

    private Gson gson;
    private BufferedReader bufferedReader;

    public ApplicationBeanConfiguration() {

    }

    @Bean
    public BufferedReader getBufferedReader() {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        return bufferedReader;
    }

    @Bean
    public Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .create();
        }

        return gson;
    }

}
