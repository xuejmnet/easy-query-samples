package com.easy.query.console;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.TableForeignKeyResult;
import com.easy.query.core.migration.TableIndexResult;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.mysql.migration.MySQLMigrationEntityParser;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * create time 2025/2/13 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyMyMigrationEntityParser extends MySQLMigrationEntityParser {
    @Override
    public @Nullable ColumnDbTypeResult getColumnDbType(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        if (String.class.equals(columnMetadata.getPropertyType())) {
            Field field = entityMetadata.getFieldByColumnMetadata(columnMetadata);
            Length lengthAnnotation = field.getAnnotation(Length.class);
            if (lengthAnnotation != null) {
                return new ColumnDbTypeResult(String.format("varchar(%s)", lengthAnnotation.max()), null);
            }
        }
        return super.getColumnDbType(entityMetadata, columnMetadata);
    }

    @Override
    public boolean isNullable(EntityMigrationMetadata entityMetadata, ColumnMetadata columnMetadata) {
        Field field = entityMetadata.getFieldByColumnMetadata(columnMetadata);
        NotNull notNullAnnotation = field.getAnnotation(NotNull.class);
        if (notNullAnnotation != null) {
            return false;
        }
        return true;
    }
}
