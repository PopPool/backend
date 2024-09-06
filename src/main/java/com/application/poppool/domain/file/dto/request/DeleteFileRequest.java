package com.application.poppool.domain.file.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteFileRequest {

    private List<String> objectKeyList;
}
