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
public class UserPhoneQueryCondition implements UserQueryCondition{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    private String telephone;
}
