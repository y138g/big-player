package cn.adrian.big.player.user.infrastructure.mapper;

import cn.adrian.big.player.user.domain.entity.UserOperateStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户操作流水表 Mapper 接口
 * @author Adrian
 */
@Mapper
public interface UserOperateStreamMapper extends BaseMapper<UserOperateStream> {

}
