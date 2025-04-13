package com.MyData.Service;

import com.MyData.Dao.TileDataDao;

import java.util.List;

public interface TileDataService {
    List<TileDataDao> getTileDetailsByUserAndFilter(String userId, String filter);
}
