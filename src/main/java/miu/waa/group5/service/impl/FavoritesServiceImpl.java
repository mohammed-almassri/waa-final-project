package miu.waa.group5.service.impl;

import miu.waa.group5.dto.FavoriteRequest;
import miu.waa.group5.dto.FavoriteResponse;
import miu.waa.group5.dto.PropertyResponse;
import miu.waa.group5.entity.Favorites;
import miu.waa.group5.entity.Media;
import miu.waa.group5.entity.Property;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.FavoritesRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.FavoritesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    @Override
    public FavoriteResponse addFavorite( FavoriteRequest request ) {

        //get user through SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username" + username));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Favorites favorite = new Favorites();
        favorite.setCustomer(user);
        favorite.setProperty(property);

        favorite = favoritesRepository.save(favorite);
        return new FavoriteResponse(favorite.getId());
    }

    @Transactional
    @Override
    public List<PropertyResponse> getCustomerFavorites() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username" + username));

        List<Favorites> favorites = favoritesRepository.findByCustomerId(user.getId());

        List<Property> properties = favorites.stream()
                .map(Favorites::getProperty)
                .toList();

        return properties.stream().map(this::convertToDto).toList();
    }

    private PropertyResponse convertToDto(Property property) {
        PropertyResponse propertyResponse = modelMapper.map(property, PropertyResponse.class);
        propertyResponse.setHomeType(property.getHomeType()==null? "":property.getHomeType().getReadableName());
        List<String> urls = property.getMedias().stream().map(Media::getUrl).toList();
        propertyResponse.setImageURLs(urls);
        propertyResponse.setOwnerId(property.getOwner().getId());
        return propertyResponse;
    }
}
