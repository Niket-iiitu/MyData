package com.MyData.Service;

import com.MyData.Dto.TileDataDto;

import java.util.List;

public interface TileDataService {
    List<TileDataDto> getTileDetailsByUserAndFilter(String userId, String filter);
}
