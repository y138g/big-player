package cn.adrian.big.player.api.user.response;

import cn.adrian.big.player.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * @author Adrian
 */
@Setter
@Getter
@ToString
public class UserQueryResponse<T> extends BaseResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    private T data;
}
