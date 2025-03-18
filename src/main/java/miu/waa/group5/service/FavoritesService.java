package miu.waa.group5.service;

import miu.waa.group5.dto.FavoriteRequest;
import miu.waa.group5.dto.FavoriteResponse;

public interface FavoritesService {
    public FavoriteResponse addFavorite(FavoriteRequest request);
}
