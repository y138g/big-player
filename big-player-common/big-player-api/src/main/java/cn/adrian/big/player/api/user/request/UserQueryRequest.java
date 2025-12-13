package cn.adrian.big.player.api.user.request;

import cn.adrian.big.player.api.user.request.condition.UserIdQueryCondition;
import cn.adrian.big.player.api.user.request.condition.UserPhoneQueryCondition;
import cn.adrian.big.player.api.user.request.condition.UserQueryCondition;
import cn.adrian.big.player.base.request.BaseRequest;
import lombok.*;

/**
 * @author Adrian
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryRequest extends BaseRequest {

    private UserQueryCondition userQueryCondition;

    public UserQueryRequest(Long userId) {
        UserIdQueryCondition userIdQueryCondition = new UserIdQueryCondition();
        userIdQueryCondition.setUserId(userId);
        this.userQueryCondition = userIdQueryCondition;
    }

    public UserQueryRequest(String telephone) {
        UserPhoneQueryCondition userPhoneQueryCondition = new UserPhoneQueryCondition();
        userPhoneQueryCondition.setTelephone(telephone);
        this.userQueryCondition = userPhoneQueryCondition;
    }
}
