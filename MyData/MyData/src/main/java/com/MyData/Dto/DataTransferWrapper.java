package com.MyData.Dto;

import lombok.Data;

@Data
public class DataTransferWrapper {
    String errorMessage;
    String errorCode;
    Object data;
}
