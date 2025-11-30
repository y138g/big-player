package cn.adrian.big.player.auth.contorller;

import cn.adrian.big.player.api.notice.response.NoticeResponse;
import cn.adrian.big.player.api.notice.service.NoticeFacadeService;
import cn.adrian.big.player.base.validator.IsMobile;
import com.alibaba.nacos.api.model.v2.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.adrian.big.player.rpc.constant.DubboConstant.DUBBO_REFERENCE_VERSION_ONE;

/**
 * 认证相关接口
 * @author Adrian
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    @DubboReference(version = DUBBO_REFERENCE_VERSION_ONE)
    private NoticeFacadeService noticeFacadeService;

    /**
     * 发送验证码
     * @param telephone
     * @return
     */
    @GetMapping("/sendCaptcha")
    public Result<Boolean> sendCaptcha(@IsMobile String telephone) {
        NoticeResponse noticeResponse = noticeFacadeService.generateAndSendSmsCaptcha(telephone);
        return Result.success(noticeResponse.getSuccess());
    }

}
