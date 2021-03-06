package indentia.monty_hall.frontend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({WebMvcConfigurationFrontend.StaticResourceConfigurationFrontend.class})
public class WebMvcConfigurationFrontend {

    private static void configureResourceLocations(ResourceHandlerRegistry registry, String... locations) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations(locations)
            .setCacheControl(CacheControl.noCache());
    }

    @Configuration
    public static class StaticResourceConfigurationFrontend extends WebMvcConfigurerAdapter {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            configureResourceLocations(registry, "classpath:/public/", "classpath:/public-robots/");
        }
    }
}
