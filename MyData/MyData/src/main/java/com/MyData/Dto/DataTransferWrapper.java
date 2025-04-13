package com.MyData.Dto;

import lombok.Data;

@Data
public class DataTransferWrapper {
    String errorMessage;
    String status;
    Object data;
}
