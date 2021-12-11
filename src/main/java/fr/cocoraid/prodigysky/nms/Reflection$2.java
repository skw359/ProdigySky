package fr.cocoraid.prodigysky.nms;

import fr.cocoraid.prodigysky.nms.Reflection.MethodInvoker;
import java.lang.reflect.Method;

class Reflection$2 implements MethodInvoker {
    Reflection$2(Method var1) {
        this.val$method = var1;
    }

    public Object invoke(Object target, Object... arguments) {
        try {
            return this.val$method.invoke(target, arguments);
        } catch (Exception var4) {
            throw new RuntimeException("Cannot invoke method " + this.val$method, var4);
        }
    }
}