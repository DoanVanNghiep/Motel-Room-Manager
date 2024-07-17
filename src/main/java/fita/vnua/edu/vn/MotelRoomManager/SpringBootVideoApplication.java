package fita.vnua.edu.vn.MotelRoomManager;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootVideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootVideoApplication.class,args);
    }

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "di1jsegss",
                "api_key", "162871499342839",
                "api_secret", "a5TSv7Ob-RDmCcMM-Pc8OCNZR-Y",
                "secure",true
        ));
        return c;
    }
}
