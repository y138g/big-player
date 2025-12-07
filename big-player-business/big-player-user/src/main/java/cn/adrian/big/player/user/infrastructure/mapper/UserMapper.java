package cn.adrian.big.player.user.infrastructure.mapper;

import cn.adrian.big.player.user.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

/**
 * user mapper
 * @author Adrian
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据昵称查询用户
     * @param nickName
     * @return
     */
    User findByNickName(@NotNull String nickName);

    /**
     * 根据邀请码查询
     * @param inviteCode
     * @return
     */
    User findByInviteCode(@NotNull String inviteCode);

    /**
     * 根据手机号查询
     * @param telephone
     * @return
     */
    User findByTelephone(@NotNull String telephone);
}
