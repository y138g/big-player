package cn.adrian.big.player.user.domain.entity.convertor;

import cn.adrian.big.player.api.user.response.data.UserInfo;
import cn.adrian.big.player.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 用户转换器（使用mapstruct简化代码）
 * @author Adrian
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 实体转VO
     * @param request
     * @return
     */
    @Mapping(target = "userId", source = "request.id")
    @Mapping(target = "createTime", source = "request.gmtCreate")
    UserInfo map2VO(User request);
}
