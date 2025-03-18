package miu.waa.group5.service;

import miu.waa.group5.dto.FavoriteRequest;
import miu.waa.group5.dto.FavoriteResponse;
import miu.waa.group5.dto.PropertyResponse;

import java.util.List;

public interface FavoritesService {
    FavoriteResponse addFavorite(FavoriteRequest request);

    List<PropertyResponse> getCustomerFavorites();
}
