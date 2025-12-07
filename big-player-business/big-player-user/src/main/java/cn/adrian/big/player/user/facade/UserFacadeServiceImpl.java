package cn.adrian.big.player.user.facade;

import cn.adrian.big.player.api.user.request.UserRegisterRequest;
import cn.adrian.big.player.api.user.response.UserOperatorResponse;
import cn.adrian.big.player.api.user.service.UserFacadeService;
import cn.adrian.big.player.rpc.facade.Facade;
import cn.adrian.big.player.user.domain.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Adrian
 */
@DubboService(version = "1.0.0")
public class UserFacadeServiceImpl implements UserFacadeService {

    @Autowired
    private UserService userService;

    @Override
    @Facade
    public UserOperatorResponse register(UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest.getTelephone(), userRegisterRequest.getInviteCode());
    }
}
