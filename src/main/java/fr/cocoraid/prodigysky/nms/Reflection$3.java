package fr.cocoraid.prodigysky.nms;

import fr.cocoraid.prodigysky.nms.Reflection.ConstructorInvoker;
import java.lang.reflect.Constructor;

class Reflection$3 implements ConstructorInvoker {
    Reflection$3(Constructor var1) {
        this.val$constructor = var1;
    }

    public Object invoke(Object... arguments) {
        try {
            return this.val$constructor.newInstance(arguments);
        } catch (Exception var3) {
            throw new RuntimeException("Cannot invoke constructor " + this.val$constructor, var3);
        }
    }
}