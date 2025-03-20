package miu.waa.group5.config;

import miu.waa.group5.entity.*;
import miu.waa.group5.repository.*;
import miu.waa.group5.service.OfferService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OfferRepository offerRepository;
    private final FavoritesRepository favoritesRepository;
    private final PasswordEncoder passwordEncoder;
    private final MediaRepository mediaRepository;


    public DatabaseSeeder(UserRepository userRepository, PropertyRepository propertyRepository,
                          OfferRepository offerRepository, FavoritesRepository favoritesRepository,
                          PasswordEncoder passwordEncoder,MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.offerRepository = offerRepository;
        this.favoritesRepository = favoritesRepository;
        this.passwordEncoder = passwordEncoder;
        this.mediaRepository = mediaRepository;
    }

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            if (offerRepository.count() == 0) {
                User admin = new User(null, "admin@example.com",
                        "$2a$12$iAVHXi.WTI9E4G.YV8m91e7KkgyvTDxFx4C4JKe8YJQEjM5g5iliu" //Aa123123
                        , "ADMIN", true, "Admin User", null, true, true, LocalDateTime.now(), LocalDateTime.now());
                User owner = new User(null, "owner@example.com", "$2a$12$iAVHXi.WTI9E4G.YV8m91e7KkgyvTDxFx4C4JKe8YJQEjM5g5iliu", "OWNER", true, "Owner User", null, true, true, LocalDateTime.now(), LocalDateTime.now());
                User customer = new User(null, "customer@example.com", "$2a$12$iAVHXi.WTI9E4G.YV8m91e7KkgyvTDxFx4C4JKe8YJQEjM5g5iliu", "CUSTOMER", true, "Customer User", null, true, true, LocalDateTime.now(), LocalDateTime.now());
                userRepository.saveAll(Arrays.asList(admin, owner, customer));

                Random random = new Random();
                String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
                String[] states = {"NY", "CA", "IL", "TX", "AZ", "PA", "TX", "CA", "TX", "CA"};
                String[] zipCodes = {"10001", "90001", "60601", "77001", "85001", "19101", "78201", "92101", "75201", "95101"};
                String[] streetNames = {"Main", "Oak", "Pine", "Maple", "Cedar", "Elm", "Willow", "Birch", "Spruce", "Hickory"};
                String[] images = {
                        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        "https://images.unsplash.com/photo-1580041065738-e72023775cdc?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA==",
                        "https://images.unsplash.com/photo-1611095210561-67f0832b1ca3?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA==",
                };
                HomeType[] homeTypes = {HomeType.HOUSE, HomeType.CONDO, HomeType.TOWN_HOME, HomeType.APARTMENT};
                StatusType[] statusTypes = {StatusType.AVAILABLE, StatusType.SOLD};

                List<Property> properties = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Property property = new Property();
                    property.setTitle("Property " + (i + 1));
                    property.setDescription("Description of property " + (i + 1));
                    property.setCity(cities[random.nextInt(cities.length)]);
                    property.setState(states[random.nextInt(states.length)]);
                    property.setZipCode(zipCodes[random.nextInt(zipCodes.length)]);
                    property.setAddress((random.nextInt(900) + 100) + " " + streetNames[random.nextInt(streetNames.length)] + " St");
                    property.setLocationLat(random.nextDouble() * 180 - 90);
                    property.setLocationLng(random.nextDouble() * 360 - 180);
                    property.setPrice(random.nextDouble() * 1000000);
                    property.setBedroomCount(random.nextInt(5) + 1);
                    property.setBathroomCount(random.nextInt(4) + 1);
                    property.setHomeType(homeTypes[random.nextInt(homeTypes.length)]);
                    property.setSquareFootage(random.nextInt(3000) + 500);
                    property.setHasParking(random.nextBoolean());
                    property.setHasPool(random.nextBoolean());
                    property.setHasAC(random.nextBoolean());
                    property.setIsApproved(true);
                    property.setProcessedAt(LocalDateTime.now());
                    property.setOwner(owner);
                    property.setStatus(statusTypes[random.nextInt(statusTypes.length)]);

                    List<Media> medias = new ArrayList<>();
                    for (int j = 0; j < 3; j++) {
                        Media media = new Media();
                        media.setFileName("image_" + (i + 1) + "_" + (j + 1) + ".jpg");
                        media.setFileType("image/jpeg");
                        media.setStorageLocation("/path/to/images");
                        media.setUrl(images[random.nextInt(images.length)]);
                        media.setSize((long) (random.nextInt(1000000) + 100000));
                        mediaRepository.save(media);
                        medias.add(media);
                    }
                    property.setMedias(medias);
                    properties.add(property);
                }
                propertyRepository.saveAll(properties);

                Offer offer1 = new Offer(null, properties.get(0), customer, 450000.0, false, LocalDateTime.now(), null, "Interested in this beautiful villa!");
                Offer offer2 = new Offer(null, properties.get(1), customer, 320000.0, false, LocalDateTime.now(), null, "I love this condo, I want to make an offer!");

                offerRepository.saveAll(Arrays.asList(offer1, offer2));

                Favorites favorite1 = new Favorites(null, customer, properties.get(0));
                Favorites favorite2 = new Favorites(null, customer, properties.get(1));

                favoritesRepository.saveAll(Arrays.asList(favorite1, favorite2));
            }
        };
    }
}
