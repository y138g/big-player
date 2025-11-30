package cn.adrian.big.player.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

import static cn.adrian.big.player.datasource.constant.DatasourceConstant.*;

/**
 * MyBatis规则重写
 * @author Adrian
 */
public class DataObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByNameIfNull(GMT_CREATE, new Date(), metaObject);
        this.setFieldValByNameIfNull(GMT_MODIFIED, new Date(), metaObject);
        this.setFieldValByName(DELETED, 0, metaObject);
        this.setFieldValByName(LOCK_VERSION, 0, metaObject);
    }

    /**
     * 当没有值的时候再设置属性，如果有值则不设置。主要是方便单元测试
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     */
    private void setFieldValByNameIfNull(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (metaObject.getValue(fieldName) == null) {
            this.setFieldValByName(fieldName, fieldVal, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(GMT_MODIFIED, new Date(), metaObject);
    }
}
