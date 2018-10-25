package indentia.monty_hall.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({WebMvcConfiguration.StaticResourceConfiguration.class})
public class WebMvcConfiguration {

    private static void configureResourceLocations(ResourceHandlerRegistry registry, String... locations) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations(locations)
            .setCacheControl(CacheControl.noCache());
    }

    @Configuration
    public static class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            configureResourceLocations(registry, "classpath:/public/", "classpath:/public-robots/");
        }
    }
}
