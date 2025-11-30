package cn.adrian.big.player.api.notice.service;


import cn.adrian.big.player.api.notice.response.NoticeResponse;

/**
 * @author Adrian
 */
public interface NoticeFacadeService {

    /**
     * 生成并发送短信验证码
     * @param telephone
     * @return
     */
    NoticeResponse generateAndSendSmsCaptcha(String telephone);
}
