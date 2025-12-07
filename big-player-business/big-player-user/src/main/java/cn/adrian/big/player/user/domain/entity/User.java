package cn.adrian.big.player.user.domain.entity;

import cn.adrian.big.player.api.user.constant.UserRole;
import cn.adrian.big.player.api.user.constant.UserStateEnum;
import cn.adrian.big.player.datasource.domain.entity.BaseEntity;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.houbb.sensitive.annotation.strategy.SensitiveStrategyPhone;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户实体
 * @author Adrian
 */
@Setter
@Getter
@TableName("user")
public class User extends BaseEntity {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String passwordHash;

    /**
     * 状态
     */
    private UserStateEnum state;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邀请人用户ID
     */
    private String inviterId;

    /**
     * 手机号
     */
    @SensitiveStrategyPhone
    private String telephone;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 头像地址
     */
    private String profilePhotoUrl;

    /**
     * 用户角色
     */
    private UserRole userRole;

    public User register(String telephone, String nickName, String password, String inviteCode, String inviterId) {
        this.setTelephone(telephone);
        this.setNickName(nickName);
        this.setPasswordHash(password != null ? DigestUtil.md5Hex(password) : null);
        this.setState(UserStateEnum.INIT);
        this.setUserRole(UserRole.CUSTOMER);
        this.setInviteCode(inviteCode);
        this.setInviterId(inviterId);
        return this;
    }

    public boolean canModifyInfo() {
        return state == UserStateEnum.INIT;
    }
}
