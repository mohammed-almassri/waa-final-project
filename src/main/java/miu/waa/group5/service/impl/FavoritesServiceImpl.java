package miu.waa.group5.service.impl;

import miu.waa.group5.dto.FavoriteRequest;
import miu.waa.group5.dto.FavoriteResponse;
import miu.waa.group5.entity.Favorites;
import miu.waa.group5.entity.Property;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.FavoritesRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;


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
}
