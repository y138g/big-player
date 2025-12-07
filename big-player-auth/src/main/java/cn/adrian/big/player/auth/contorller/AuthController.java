package cn.adrian.big.player.auth.contorller;

import cn.adrian.big.player.api.notice.response.NoticeResponse;
import cn.adrian.big.player.api.notice.service.NoticeFacadeService;
import cn.adrian.big.player.api.user.request.UserRegisterRequest;
import cn.adrian.big.player.api.user.response.UserOperatorResponse;
import cn.adrian.big.player.api.user.service.UserFacadeService;
import cn.adrian.big.player.auth.exception.AuthException;
import cn.adrian.big.player.auth.param.RegisterParam;
import cn.adrian.big.player.base.validator.IsMobile;
import cn.adrian.big.player.web.vo.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import static cn.adrian.big.player.api.notice.constat.NoticeConstant.CAPTCHA_KEY_PREFIX;
import static cn.adrian.big.player.auth.exception.AuthErrorCode.VERIFICATION_CODE_WRONG;

/**
 * 认证相关接口
 * @author Adrian
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    @DubboReference(version = "1.0.0")
    private NoticeFacadeService noticeFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 发送验证码
     * @param telephone
     * @return
     */
    @GetMapping("/sendCaptcha")
    public Result<Boolean> sendCaptcha(@IsMobile String telephone) {
        log.info("开始发送验证码，手机号: {}", telephone);
        NoticeResponse noticeResponse = noticeFacadeService.generateAndSendSmsCaptcha(telephone);
        log.info("验证码发送结果: {}", noticeResponse.getSuccess());
        return Result.success(noticeResponse.getSuccess());
    }

    /**
     * 注册用户
     * @param registerParam
     * @return
     */
    @PostMapping("/register")
    public Result<Boolean> register(@Valid @RequestBody RegisterParam registerParam) {
        log.info("开始注册用户，手机号: {}", registerParam.getTelephone());

        //验证码校验
        String cachedCode = redisTemplate.opsForValue().get(CAPTCHA_KEY_PREFIX + registerParam.getTelephone());

        if (!StringUtils.equalsIgnoreCase(cachedCode, registerParam.getCaptcha())) {
            log.warn("验证码错误，缓存中的验证码: {}，输入的验证码: {}", cachedCode, registerParam.getCaptcha());
            throw new AuthException(VERIFICATION_CODE_WRONG);
        }

        //注册
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setTelephone(registerParam.getTelephone());
        userRegisterRequest.setInviteCode(registerParam.getInviteCode());

        UserOperatorResponse registerResult = userFacadeService.register(userRegisterRequest);
        log.info("用户注册结果: {}", registerResult.getSuccess());
        if (registerResult.getSuccess()) return Result.success(true);

        return Result.error(registerResult.getResponseCode(), registerResult.getResponseMessage());
    }
}
