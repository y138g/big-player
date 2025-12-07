package cn.adrian.big.player.api.user.response.data;

import cn.adrian.big.player.api.user.constant.UserRole;
import cn.adrian.big.player.api.user.constant.UserStateEnum;
import com.github.houbb.sensitive.annotation.strategy.SensitiveStrategyPhone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Adrian
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BasicUserInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @SensitiveStrategyPhone
    private String telephone;

    /**
     * 状态
     * @see UserStateEnum
     */
    private String state;

    /**
     * 用户角色
     */
    private UserRole userRole;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 判断用户是否可以购买
     * @return
     */
    public boolean userCanBuy() {

        // 普通用户可以购买商品，管理员不能购买
        if (getUserRole() == null || !getUserRole().equals(UserRole.CUSTOMER)) return false;

        // 若用户被封禁，无法购买
        return getState() != null;
    }
}
