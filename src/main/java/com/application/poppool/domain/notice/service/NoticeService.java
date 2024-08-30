package com.application.poppool.domain.notice.service;


import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import com.application.poppool.domain.notice.entity.NoticeEntity;
import com.application.poppool.domain.notice.repository.NoticeRepository;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 리스트 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public GetNoticeListResponse getNoticeList() {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAll();

        List<GetNoticeListResponse.NoticeInfo> noticeInfoList = noticeEntityList.stream()
                .map(noticeEntity -> GetNoticeListResponse.NoticeInfo.builder()
                        .id(noticeEntity.getId())
                        .title(noticeEntity.getTitle())
                        .createdDateTime(noticeEntity.getCreateDateTime())
                        .build())
                .toList();

        return GetNoticeListResponse.builder().noticeInfoList(noticeInfoList).build();
    }

    /**
     * 공지사항 상세 조회
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public GetNoticeDetailResponse getNoticeDetail(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        return GetNoticeDetailResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createDateTime(notice.getCreateDateTime())
                .build();
    }

}
