package cn.adrian.big.player.user.facade;

import cn.adrian.big.player.api.user.request.UserQueryRequest;
import cn.adrian.big.player.api.user.request.UserRegisterRequest;
import cn.adrian.big.player.api.user.request.condition.UserIdQueryCondition;
import cn.adrian.big.player.api.user.request.condition.UserPhoneQueryCondition;
import cn.adrian.big.player.api.user.response.UserOperatorResponse;
import cn.adrian.big.player.api.user.response.UserQueryResponse;
import cn.adrian.big.player.api.user.response.data.UserInfo;
import cn.adrian.big.player.api.user.service.UserFacadeService;
import cn.adrian.big.player.rpc.facade.Facade;
import cn.adrian.big.player.user.domain.entity.User;
import cn.adrian.big.player.user.domain.entity.convertor.UserConvertor;
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
    public UserQueryResponse<UserInfo> query(UserQueryRequest userQueryRequest) {

        // 抽象查询接口，根据不同实现类执行不同的查询逻辑
        // switch表达式适用于简单逻辑实现，有扩展性但不遵循开闭原则
        // 如果逻辑复杂，且对开闭原则要求比较严格，可以采用 枚举+抽象工厂+策略模式 优化实现
        User user = switch (userQueryRequest.getUserQueryCondition()) {
            case UserIdQueryCondition userIdQueryCondition:
                yield userService.findById(userIdQueryCondition.getUserId());
            case UserPhoneQueryCondition userPhoneQueryCondition:
                yield userService.findByTelephone(userPhoneQueryCondition.getTelephone());
            default:
                throw new UnsupportedOperationException(userQueryRequest.getUserQueryCondition() + " is not supported");
        };

        UserInfo userInfo = UserConvertor.INSTANCE.map2VO(user);
        UserQueryResponse<UserInfo> userQueryResponse = new UserQueryResponse<>();
        userQueryResponse.setData(userInfo);
        userQueryResponse.setSuccess(true);
        return userQueryResponse;
    }

    @Override
    @Facade
    public UserOperatorResponse register(UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest.getTelephone(), userRegisterRequest.getInviteCode());
    }
}
