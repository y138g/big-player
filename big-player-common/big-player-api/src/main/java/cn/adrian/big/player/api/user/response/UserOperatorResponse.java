package cn.adrian.big.player.api.user.response;

import cn.adrian.big.player.api.user.response.data.UserInfo;
import cn.adrian.big.player.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户操作响应
 * @author Adrian
 */
@Getter
@Setter
public class UserOperatorResponse extends BaseResponse {

    private UserInfo user;
}
