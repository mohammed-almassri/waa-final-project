package miu.waa.group5.service;

import miu.waa.group5.dto.FavoriteRequest;
import miu.waa.group5.dto.FavoriteResponse;
import miu.waa.group5.dto.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoritesService {
    FavoriteResponse addFavorite(FavoriteRequest request);

    public void deleteFavorite(Long favoriteId);

    Page<PropertyResponse> getCustomerFavorites(Pageable pageable);
}
