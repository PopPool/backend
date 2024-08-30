package com.application.poppool.domain.admin.notice.service;

import com.application.poppool.domain.admin.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.admin.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.notice.entity.NoticeEntity;
import com.application.poppool.domain.notice.repository.NoticeRepository;
import com.application.poppool.domain.user.entity.UserEntity;
import com.application.poppool.domain.user.entity.UserRoleEntity;
import com.application.poppool.domain.user.repository.UserRepository;
import com.application.poppool.global.exception.BadRequestException;
import com.application.poppool.global.exception.ErrorCode;
import com.application.poppool.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

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
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTICE_NOT_FOUND));

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
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTICE_NOT_FOUND));

        UserEntity user = userRepository.findByUserId(adminId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 해당 유저의 권한 가져오기
        Set<UserRoleEntity> userRoleList = user.getUserRoles();

        // 관리자 여부 체크
        boolean isAdmin = userRoleList.stream().anyMatch(role -> role.getUserRole().name().equals("ADMIN"));

        // 관리자 권한 가진 유저만 삭제할 수 있도록 검증
        if (!isAdmin) {
            throw new BadRequestException(ErrorCode.NOT_ADMIN);
        }

        // 공지사항 삭제
        noticeRepository.delete(notice);
    }

}
