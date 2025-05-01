package com.MyData.Dto;

import com.MyData.Dao.NotesDataDao;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class NotesDataDto {
    private String title;
    private List<String> tags;
    private String category;
    private String data;

    public static List<NotesDataDto> fromDaoList(List<NotesDataDao> daoList) {
        return daoList.stream().map(dao -> {
            NotesDataDto dto = new NotesDataDto();
            dto.setTitle(dao.getTitle());
            dto.setTags(dao.getTags());
            dto.setCategory(dao.getCategory());
            dto.setData(dao.getData());
            return dto;
        }).collect(Collectors.toList());
    }
}
