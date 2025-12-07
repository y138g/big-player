package cn.adrian.big.player.user.domain.service;

import cn.adrian.big.player.api.user.constant.UserOperateTypeEnum;
import cn.adrian.big.player.user.domain.entity.User;
import cn.adrian.big.player.user.domain.entity.UserOperateStream;
import cn.adrian.big.player.user.infrastructure.mapper.UserOperateStreamMapper;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户操作流水表 服务类
 * @author Adrian
 */
@Service
public class UserOperateStreamService extends ServiceImpl<UserOperateStreamMapper, UserOperateStream> {

    public Long insertStream(User user, UserOperateTypeEnum type) {
        UserOperateStream stream = new UserOperateStream();
        stream.setUserId(String.valueOf(user.getId()));
        stream.setOperateTime(new Date());
        stream.setType(type.name());
        stream.setParam(JSON.toJSONString(user));
        boolean result = save(stream);
        if (!result) return null;
        return stream.getId();
    }
}
