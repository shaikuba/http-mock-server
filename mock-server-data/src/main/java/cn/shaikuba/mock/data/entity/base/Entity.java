package cn.shaikuba.mock.data.entity.base;

import com.google.gson.annotations.Expose;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static cn.shaikuba.mock.common.exception.ExceptionUtils.processException;

@Data
public class Entity<M> implements Serializable {

    @Expose
    private Long id;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * This function is used to check whether the entity id exist. It is not
     * used to check the entity existence in DB. It can be used to check the
     * entity in controller, which is passed from page.
     *
     * @return true if exists
     */
    public boolean exist() {
        return id != null && id != 0;
    }

    /**
     * Merge source entity into current entity.
     * <p>
     * Only not null value is merged.
     *
     * @param source merge source
     * @return merged entity
     */
    public M merge(M source) {
        Field forDisplay = null;
        try {
            Field[] fields = getClass().getDeclaredFields();
            // Iterate over all the attributes
            for (Field each : fields) {
                if (each.isSynthetic()) {
                    continue;
                }
                final int modifiers = each.getModifiers();
                if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                    continue;
                }
                forDisplay = each;
                if (!each.isAccessible()) {
                    each.setAccessible(true);
                }
                final Object value = each.get(source);
                if (value != null) {
                    each.set(this, value);
                }
            }
            return (M) this;
        } catch (Exception e) {
            String displayName = (forDisplay == null) ? "Empty" : forDisplay.getName();
            throw processException(displayName + " - Exception occurred while merging an entity from " + source + " to "
                    + this, e);
        }
    }


    /**
     * Clone current entity.
     * <p>
     * Only not null value is merged.
     *
     * @param toInstance instance to which the value is copied.
     * @return cloned entity
     */
    public M cloneTo(M toInstance) {
        Field forDisplay = null;
        try {
            Field[] fields = getClass().getDeclaredFields();
            // Iterate over all the attributes
            for (Field each : fields) {
                if (each.isSynthetic()) {
                    continue;
                }
                final int modifiers = each.getModifiers();
                if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                    continue;
                }
                forDisplay = each;
                final Cloneable annotation = each.getAnnotation(Cloneable.class);
                if (annotation == null) {
                    continue;
                }
                if (!each.isAccessible()) {
                    each.setAccessible(true);
                }
                each.set(toInstance, each.get(this));
            }
            return toInstance;
        } catch (Exception e) {
            String displayName = (forDisplay == null) ? "Empty" : forDisplay.getName();
            throw processException(displayName + " - Exception occurred while cloning an entity from " + this + " to "
                    + toInstance, e);
        }
    }
}
