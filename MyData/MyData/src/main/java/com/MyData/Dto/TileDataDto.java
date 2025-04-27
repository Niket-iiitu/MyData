package com.MyData.Dto;

import com.MyData.Dao.NotesDataDao;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TileDataDto {
    private String title;
    private List<String> tags;
    private String category;
    private String data;

    public static List<TileDataDto> fromDaoList(List<NotesDataDao> daoList) {
        return daoList.stream().map(dao -> {
            TileDataDto dto = new TileDataDto();
            dto.setTitle(dao.getTitle());
            dto.setTags(dao.getTags());
            dto.setCategory(dao.getCategory());
            dto.setData(dao.getData());
            return dto;
        }).collect(Collectors.toList());
    }
}
