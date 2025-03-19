package miu.waa.group5.config;

import miu.waa.group5.entity.*;
import miu.waa.group5.repository.FavoritesRepository;
import miu.waa.group5.repository.OfferRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.OfferService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;
    private final FavoritesRepository favoritesRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PropertyRepository propertyRepository,
                          OfferRepository offerRepository, FavoritesRepository favoritesRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.offerRepository = offerRepository;
        this.favoritesRepository = favoritesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            if (offerRepository.count() == 0) {
                User admin = new User(null, "admin@example.com", passwordEncoder.encode("Aa123123"), "ADMIN", true, "Admin User", null, true, true, LocalDateTime.now(), LocalDateTime.now());
                User owner = new User(null, "owner@example.com", passwordEncoder.encode("Aa123123"), "OWNER", true, "Owner User", null, true, true, LocalDateTime.now(), LocalDateTime.now());
                User customer = new User(null, "customer@example.com", passwordEncoder.encode("Aa123123"), "CUSTOMER", true, "Customer User", null, true, true, LocalDateTime.now(), LocalDateTime.now());

                userRepository.saveAll(Arrays.asList(admin, owner, customer));

                Property property1 = new Property();
                property1.setTitle("Beautiful Villa");
                property1.setDescription("A luxurious villa in the city");
                property1.setCity("New York");
                property1.setState("NY");
                property1.setZipCode("10001");
                property1.setAddress("123 Villa St");
                property1.setLocationLat(40.7128);
                property1.setLocationLng(-74.0060);
                property1.setPrice(500000.0);
                property1.setBedroomCount(4);
                property1.setBathroomCount(3);
                property1.setHomeType(HomeType.HOUSE); // Enum: HOUSE, TOWNHOME, CONDO, etc.
                property1.setSquareFootage(3500);
                property1.setHasParking(true);
                property1.setHasPool(true);
                property1.setHasAC(true);
                property1.setIsApproved(true);
                property1.setProcessedAt(LocalDateTime.now());
                property1.setOwner(owner); // Set the owner for the property
                property1.setStatus(StatusType.AVAILABLE); // Enum: AVAILABLE, SOLD, etc.

                Property property2 = new Property();
                property2.setTitle("Modern Condo");
                property2.setDescription("A modern condo with a pool");
                property2.setCity("Los Angeles");
                property2.setState("CA");
                property2.setZipCode("90001");
                property2.setAddress("456 Condo Ave");
                property2.setLocationLat(34.0522);
                property2.setLocationLng(-118.2437);
                property2.setPrice(350000.0);
                property2.setBedroomCount(2);
                property2.setBathroomCount(2);
                property2.setHomeType(HomeType.CONDO); // Enum: HOUSE, TOWNHOME, CONDO, etc.
                property2.setSquareFootage(1200);
                property2.setHasParking(true);
                property2.setHasPool(true);
                property2.setHasAC(true);
                property2.setIsApproved(true);
                property2.setProcessedAt(LocalDateTime.now());
                property2.setOwner(owner); // Set the owner for the property
                property2.setStatus(StatusType.AVAILABLE); // Enum: AVAILABLE, SOLD, etc.

                propertyRepository.saveAll(Arrays.asList(property1, property2));

                Offer offer1 = new Offer(null, property1, customer, 450000.0, false, LocalDateTime.now(), null, "Interested in this beautiful villa!");
                Offer offer2 = new Offer(null, property2, customer, 320000.0, false, LocalDateTime.now(), null, "I love this condo, I want to make an offer!");

                offerRepository.saveAll(Arrays.asList(offer1, offer2));

                Favorites favorite1 = new Favorites(null, customer, property1);
                Favorites favorite2 = new Favorites(null, customer, property2);

                favoritesRepository.saveAll(Arrays.asList(favorite1, favorite2));
            }
        };
    }
}
