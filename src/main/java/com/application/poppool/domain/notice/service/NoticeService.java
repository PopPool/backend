package com.application.poppool.domain.notice.service;


import com.application.poppool.domain.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import com.application.poppool.domain.notice.entity.NoticeEntity;
import com.application.poppool.domain.notice.repository.NoticeRepository;
import com.application.poppool.global.exception.BadRequestException;
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

    /**
     * 공지사항 작성
     *
     * @param request
     */
    @Transactional
    public void createNotice(CreateNoticeRequest request) {
        NoticeEntity notice = NoticeEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        noticeRepository.save(notice);
    }

    /**
     * 공지사항 수정
     *
     * @param id
     * @param request
     */
    @Transactional
    public void updateNotice(Long id, UpdateNoticeRequest request) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        // 공지사항 수정
        notice.updateNotice(request);
    }

    /**
     * 공지사항 삭제
     *
     * @param id
     * @param adminId
     */
    @Transactional
    public void deleteNotice(Long id, String adminId) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DATA_NOT_FOUND));

        // 관리자만 삭제할 수 있도록 검증
        if (!adminId.equals(notice.getCreator()) || !adminId.equals("admin")) {
            throw new BadRequestException(ErrorCode.NOT_ADMIN);
        }

        // 공지사항 삭제
        noticeRepository.delete(notice);
    }

}
