package cn.adrian.big.player.api.user.service;


import cn.adrian.big.player.api.user.request.UserRegisterRequest;
import cn.adrian.big.player.api.user.response.UserOperatorResponse;

/**
 * 用户Facade
 * @author Adian
 */
public interface UserFacadeService {

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    UserOperatorResponse register(UserRegisterRequest userRegisterRequest);
}
