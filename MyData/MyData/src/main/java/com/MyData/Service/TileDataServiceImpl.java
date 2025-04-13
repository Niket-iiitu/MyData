package com.MyData.Service;

import ch.qos.logback.core.util.StringUtil;
import com.MyData.Dao.TileDataDao;
import com.MyData.Dto.TileDataDto;
import com.MyData.Repository.TileDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileDataServiceImpl implements TileDataService{
    @Autowired
    TileDataRepo tileDataRepo;

    @Override
    public List<TileDataDao> getTileDetailsByUserAndFilter(String userId, String filter){
        List<TileDataDao> listOfTiles = new ArrayList<>();
        if(StringUtil.notNullNorEmpty(userId) && StringUtil.notNullNorEmpty(filter)){
            List<TileDataDao> dbResponse = tileDataRepo.findByUserIdAndCategory(userId, filter);
            if(dbResponse!=null){
                listOfTiles = dbResponse;
            }
            else{
                System.out.println("[WARNING] TileDataServiceImpl.java getTileDetailsByUserAndFilter: No data found for given userID and filter.");
            }
        }
        else{
            System.out.println("[WARNING] TileDataServiceImpl.java getTileDetailsByUserAndFilter: userId or filter is blank.");
        }
        return listOfTiles;
    }
}
