package fr.cocoraid.prodigysky.nms;

import fr.cocoraid.prodigysky.nms.Reflection.FieldAccessor;
import java.lang.reflect.Field;

class Reflection$1 implements FieldAccessor<T> {
    Reflection$1(Field var1) {
        this.val$field = var1;
    }

    public T get(Object target) {
        try {
            return this.val$field.get(target);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException("Cannot access reflection.", var3);
        }
    }

    public void set(Object target, Object value) {
        try {
            this.val$field.set(target, value);
        } catch (IllegalAccessException var4) {
            throw new RuntimeException("Cannot access reflection.", var4);
        }
    }

    public boolean hasField(Object target) {
        return this.val$field.getDeclaringClass().isAssignableFrom(target.getClass());
    }
}