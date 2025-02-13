package com.easy.query.console;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.util.EasyClassUtil;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

/**
 * create time 2025/2/13 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyMyMigrationEntityParser implements MigrationEntityParser {
    @Override
    public @Nullable ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        if (String.class.equals(columnMetadata.getPropertyType())) {
            Field field = entityMetadata.getFieldByName(columnMetadata);
            Length lengthAnnotation = field.getAnnotation(Length.class);
            if (lengthAnnotation != null) {
                return new ColumnDbTypeResult(String.format("varchar(%s)", lengthAnnotation.max()), null);
            }
        }
        return null;
    }

    @Override
    public String getColumnComment(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return "";
    }

    @Override
    public Boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        Field field = entityMetadata.getFieldByName(columnMetadata);
        NotNull notNullAnnotation = field.getAnnotation(NotNull.class);
        if (notNullAnnotation != null) {
            return false;
        }
        return null;
    }

    @Override
    public Boolean columnExistInDb(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return null;
    }

    @Override
    public String getTableComment(EntityMigrationMetadata entityMetadata) {
        return "";
    }

    @Override
    public String getColumnRenameFrom(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return "";
    }
}
