package cn.adrian.big.player.notice.domain.service;

import cn.adrian.big.player.base.exception.BizException;
import cn.adrian.big.player.notice.domain.constant.NoticeState;
import cn.adrian.big.player.notice.domain.constant.NoticeType;
import cn.adrian.big.player.notice.domain.entity.Notice;
import cn.adrian.big.player.notice.infrastructure.mapper.NoticeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static cn.adrian.big.player.base.exception.BizErrorCode.NOTICE_SAVE_FAILED;

/**
 * 通知服务
 * @author Adrian
 */
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    private static final String SMS_NOTICE_TITLE = "验证码";

    public Page<Notice> pageQueryForRetry(int currentPage, int pageSize) {
        Page<Notice> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.in("state", NoticeState.INIT.name(), NoticeState.FAILED);
        wrapper.orderBy(true, true, "gmt_create");
        return this.page(page, wrapper);
    }


    public Notice saveCaptcha(String telephone, String captcha) {
        Notice notice = Notice.builder()
                .noticeTitle(SMS_NOTICE_TITLE)
                .noticeContent(captcha)
                .noticeType(NoticeType.SMS)
                .targetAddress(telephone)
                .state(NoticeState.INIT)
                .build();

        if (!this.save(notice)) {
            throw new BizException(NOTICE_SAVE_FAILED);
        }

        return notice;
    }
}
