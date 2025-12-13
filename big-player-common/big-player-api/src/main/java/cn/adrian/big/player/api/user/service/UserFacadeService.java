package cn.adrian.big.player.api.user.service;


import cn.adrian.big.player.api.user.request.UserQueryRequest;
import cn.adrian.big.player.api.user.request.UserRegisterRequest;
import cn.adrian.big.player.api.user.response.UserOperatorResponse;
import cn.adrian.big.player.api.user.response.UserQueryResponse;
import cn.adrian.big.player.api.user.response.data.UserInfo;

/**
 * 用户Facade
 * @author Adian
 */
public interface UserFacadeService {

    /**
     * 查询用户信息
     * @param userQueryRequest
     * @return
     */
    UserQueryResponse<UserInfo> query(UserQueryRequest userQueryRequest);

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    UserOperatorResponse register(UserRegisterRequest userRegisterRequest);
}
