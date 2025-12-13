package cn.adrian.big.player.api.user.request.condition;

import lombok.*;

import java.io.Serial;

/**
 * @author Adrian
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserIdQueryCondition implements UserQueryCondition {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
}
