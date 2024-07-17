package com.application.poppool.domain.user.repository;

import com.application.poppool.domain.user.dto.response.GetBlockedUserListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlockedUserRepositoryCustom {

    Page<GetBlockedUserListResponse.BlockedUserInfo> getBlockedUserList(String userId, Pageable pageable);

}
