package cn.adrian.big.player.base.validator;


import cn.hutool.core.lang.Validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 手机号校验器
 * @author Adrian
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Validator.isMobile(value);
    }
}
