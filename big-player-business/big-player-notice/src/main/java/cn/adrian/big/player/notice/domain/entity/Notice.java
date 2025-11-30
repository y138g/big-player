package cn.adrian.big.player.notice.domain.entity;

import cn.adrian.big.player.datasource.domain.entity.BaseEntity;
import cn.adrian.big.player.notice.domain.constant.NoticeState;
import cn.adrian.big.player.notice.domain.constant.NoticeType;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author Hollis
 */
@Setter
@Getter
public class Notice extends BaseEntity {

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 通知内容
     */
    private String noticeContent;

    /**
     * 通知类型
     */
    private NoticeType noticeType;

    /**
     * 发送成功时间
     */
    private Date sendSuccessTime;
    /**
     * 接收地址
     */
    private String targetAddress;
    /**
     * 状态
     */
    private NoticeState state;

    /**
     * 重试次数
     */
    private int retryTimes;

    /**
     * 扩展信息
     */
    private String extendInfo;

    public void addExtendInfo(String key, String value) {
        Map<String, String> extendInfoMap;
        if (extendInfo == null) {
            extendInfoMap = Maps.newHashMapWithExpectedSize(1);
        } else {
            extendInfoMap = JSON.parseObject(this.extendInfo, Map.class);
        }
        extendInfoMap.put(key, value);
        this.extendInfo = JSON.toJSONString(extendInfoMap);
    }

    public static class Builder {
        private String noticeTitle;
        private String noticeContent;
        private NoticeType noticeType;
        private Date sendSuccessTime;
        private String targetAddress;
        private NoticeState state;
        private String extendInfo;

        public Builder noticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
            return this;
        }

        public Builder noticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
            return this;
        }

        public Builder noticeType(NoticeType noticeType) {
            this.noticeType = noticeType;
            return this;
        }

        public Builder sendSuccessTime(Date sendSuccessTime) {
            this.sendSuccessTime = sendSuccessTime;
            return this;
        }

        public Builder targetAddress(String targetAddress) {
            this.targetAddress = targetAddress;
            return this;
        }

        public Builder state(NoticeState state) {
            this.state = state;
            return this;
        }

        public Builder extendInfo(String extendInfo) {
            this.extendInfo = extendInfo;
            return this;
        }

        public Notice build() {
            Notice notice = new Notice();
            notice.noticeTitle = this.noticeTitle;
            notice.noticeContent = this.noticeContent;
            notice.noticeType = this.noticeType;
            notice.sendSuccessTime = this.sendSuccessTime;
            notice.targetAddress = this.targetAddress;
            notice.state = this.state;
            notice.extendInfo = this.extendInfo;
            return notice;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
