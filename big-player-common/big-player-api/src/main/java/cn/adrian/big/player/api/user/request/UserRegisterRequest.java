package cn.adrian.big.player.api.user.request;

import cn.adrian.big.player.base.request.BaseRequest;
import lombok.*;

/**
 * 用户注册请求
 * @author Adrian
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest extends BaseRequest {

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 密码
     */
    private String password;

}
