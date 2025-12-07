package cn.adrian.big.player.auth.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Adrian
 */
@Setter
@Getter
public class LoginParam extends RegisterParam {

    /**
     * 记住我
     */
    private Boolean rememberMe;
}
