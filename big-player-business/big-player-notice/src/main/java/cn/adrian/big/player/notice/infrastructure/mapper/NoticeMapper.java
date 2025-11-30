package cn.adrian.big.player.notice.infrastructure.mapper;

import cn.adrian.big.player.notice.domain.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知操作 Mapper 接口
 * @author Adrian
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}
