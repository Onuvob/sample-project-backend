package com.sampleproject.util;

import lombok.Data;


@Data
public class RequestUtil {
    private String code;
    private String name;
    private String phone;
    private String status;
    private String source;
    private String destination;
    public int pageNum = 1;
    public int pageSize = 10;
    public String sortField = "id";
    public String sortDir = "desc";
}
