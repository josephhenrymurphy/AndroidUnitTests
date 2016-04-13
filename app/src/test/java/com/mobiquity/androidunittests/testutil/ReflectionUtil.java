package com.mobiquity.androidunittests.testutil;

import android.annotation.SuppressLint;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertEquals;

/**
 * Collection of Reflection Utils.
 */
@SuppressLint("NewApi")
public final class ReflectionUtil {
    /**
     * Private Constructor.
     * Do not instantiate Utility classes.
     */
    private ReflectionUtil() {
        throw new RuntimeException("Do not instantiate me");
    }

    /**
     * Gets the value of the named field from the class.
     *
     * @param clazz Class definition that field is declared in.
     * @param name  name of field.
     * @param <T>   expected value type.
     * @return the value of the field in the class.
     */
    public static <T> T getStaticField(final Class<?> clazz, final String name) {
        return getField(clazz, null, name);
    }

    /**
     * Gets the value of the named field from the instance.
     * NOTE: use this only when the instance class had the field declared there.
     *
     * @param instance instance to get value from.
     * @param name     name of field.
     * @param <T>      expected value type.
     * @return the value of the field in the instance.
     */
    public static <T> T getField(final Object instance, final String name) {
        return getField(instance.getClass(), instance, name);
    }

    /**
     * Gets the value of the named field from the instance.
     *
     * @param clazz    Class definition that field is declared in.
     * @param instance instance to get value from.
     * @param name     name of field.
     * @param <T>      expected value type.
     * @return the value of the field in the instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(final Class<?> clazz, final Object instance, final String name) {
        try {
            final Field f = clazz.getDeclaredField(name);
            final boolean accessibility = setAccessible(f, true);
            final Object value = f.get(instance);
            setAccessible(f, accessibility);
            return (T) value;
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set a static field value.
     *
     * @param clazz class the field is defined in.
     * @param name  name of field.
     * @param value value to set field on.
     * @param <T>   expected type of field.
     * @return the old value of the field.
     */
    public static <T> T setStaticField(final Class<?> clazz, final String name, final Object value) {
        return setField(clazz, null, name, value);
    }

    /**
     * Set a field value.
     *
     * @param instance instance to set field on and has the field declared in.
     * @param name     name of field.
     * @param value    value to set field on.
     * @param <T>      expected type of field.
     * @return the old value of the field.
     */
    public static <T> T setField(final Object instance, final String name, final Object value) {
        return setField(instance.getClass(), instance, name, value);
    }

    /**
     * Set a field value.
     *
     * @param clazz    class the field is defined in.
     * @param instance instance to set field on.
     * @param name     name of field.
     * @param value    value to set field on.
     * @param <T>      expected type of field.
     * @return the old value of the field.
     */
    @SuppressWarnings("unchecked")
    public static <T> T setField(
            final Class<?> clazz, final Object instance, final String name, final Object value) {

        try {
            final Field f = clazz.getDeclaredField(name);
            final boolean accessibility = setAccessible(f, true);
            final boolean finality = setFinal(f, false);
            final Object old = f.get(instance);
            f.set(instance, value);
            setFinal(f, finality);
            setAccessible(f, accessibility);
            return (T) old;
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a method definition from the declaring class.
     *
     * @param clazz          class that declares the method.
     * @param name           name of the method.
     * @param parameterTypes parameter types.
     * @return the method that was found.
     */
    public static Method getMethod(
            final Class<?> clazz, final String name, final Class<?>... parameterTypes) {

        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invokes a static method.
     *
     * @param method     method to invoke.
     * @param parameters parameters to invoke with.
     * @param <T>        the expected return type.
     * @return the returned value.
     */
    public static <T> T invokeStaticMethod(final Method method, final Object... parameters) {
        return invokeMethod(method, null, parameters);
    }

    /**
     * Invokes a method.
     *
     * @param method     method to invoke.
     * @param instance   instance to invoke method on.
     * @param parameters parameters to invoke with.
     * @param <T>        the expected return type.
     * @return the returned value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(
            final Method method, final Object instance, final Object... parameters) {

        try {
            final boolean accessibility = setAccessible(method, true);
            final Object result = method.invoke(instance, parameters);
            setAccessible(method, accessibility);
            return (T) result;
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Change the accessibility of a field.
     *
     * @param accessObject  accessObject to change accessibility of.
     * @param accessibility value to change accessibility of.
     * @return the old accessibility value.
     */
    private static boolean setAccessible(
            final AccessibleObject accessObject, final boolean accessibility) {

        final boolean old = accessObject.isAccessible();
        accessObject.setAccessible(accessibility);
        return old;
    }

    /**
     * Changes the finality of a field.
     *
     * @param f         field to change the finality on.
     * @param makeFinal true to make final, false to make non-final.
     * @return the old value of the final modifier for the field.
     */
    private static boolean setFinal(final Field f, final boolean makeFinal) {
        final int mods = f.getModifiers();
        final boolean isFinal = (mods & Modifier.FINAL) != 0;
        try {
            final Field modifiers = f.getClass().getDeclaredField("modifiers");
            final boolean accessible = setAccessible(modifiers, true);
            modifiers.setInt(f, makeFinal
                    ? mods | Modifier.FINAL : mods & ~Modifier.FINAL);
            setAccessible(modifiers, accessible);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return isFinal;
    }

    /**
     * This is the helper method to create a private object that has no arguments
     *
     * @param clazz class for which private constructor needs to be invoked.
     * @param <T>   the expected return type.
     * @return the returned value.
     */
    public static <T> T invokePrivateConstructor(Class<T> clazz) {
        return invokePrivateConstructor(clazz, new Class[]{});
    }

    /**
     * This is the helper method to create a private object
     *
     * @param clazz          class for which private constructor needs to be invoked.
     * @param parameterTypes the parameter types of the requested constructor.
     * @param args           the arguments to the method
     * @param <T>            the returned value.
     * @return
     */
    public static <T> T invokePrivateConstructor(Class<T> clazz, Class[] parameterTypes, Object... args) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (final IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used to cover methods that are generated for enums by the compiler
     *
     * @param enumClass The enum class
     */
    public static void verifyEnumStatics(Class enumClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method valuesMethod = enumClass.getMethod("values");
        valuesMethod.setAccessible(true);
        Object[] values = (Object[]) valuesMethod.invoke(null);
        Method valueOfMethod = enumClass.getMethod("valueOf", String.class);
        valueOfMethod.setAccessible(true);
        assertEquals(values[0], valueOfMethod.invoke(null, ((Enum) values[0]).name()));
    }

}