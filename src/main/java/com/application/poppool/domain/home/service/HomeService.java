package com.application.poppool.domain.home.service;

import com.application.poppool.domain.home.dto.response.GetHomeInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class HomeService {


    public GetHomeInfoResponse getHomeInfo(@PathVariable String userId) {
        return null;
    }
}
