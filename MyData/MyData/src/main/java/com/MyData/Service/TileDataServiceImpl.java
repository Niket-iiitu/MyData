package com.MyData.Service;

import ch.qos.logback.core.util.StringUtil;
import com.MyData.Dao.TileDataDao;
import com.MyData.Dto.TileDataDto;
import com.MyData.Repository.TileDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<String> getListOfCategories(String userId){
        List<String> listOfCategories = new ArrayList<>();
        if(StringUtil.notNullNorEmpty(userId)){
            List<String> dbResponse = tileDataRepo.getCategoriesByUserId(userId);
            if(dbResponse!=null && dbResponse.size()>0){
                listOfCategories = dbResponse;
                if(listOfCategories.contains("Default")){
                    listOfCategories.remove("Default");
                }
                Collections.sort(listOfCategories);
                listOfCategories.add(0, "Default");
            }
            else{
                listOfCategories.add("Default");
            }
        }
        else{
            System.out.println("[WARNING] TileDataServiceImpl.java getListOfCategories: userId is blank.");
        }
        return listOfCategories;
    }
}
