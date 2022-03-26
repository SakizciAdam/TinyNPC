package tk.sakizciadam.tinynpc.api.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {

    public static Object invoke(Class clazz,Object obj,String name,Class[] classes,Object... objects){

        for(Method method: clazz.getDeclaredMethods()){
            try {
                if(method==null) continue;

                if(method.getName()!=name) continue;
                if(method.getParameterCount()!=classes.length) continue;
                if(!Arrays.equals(classes,method.getParameterTypes())) continue;

                method.setAccessible(true);

                return method.invoke(obj,objects);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;


    }

    public static Object invoke(Class clazz,Object obj,String name){
        try {
            Method method=clazz.getDeclaredMethod(name);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object get(Class clazz,Object obj,String name){
        try {
            Field field=clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(Class clazz,Object object,String name,Object value){
        try {
            Field field=clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    public static void set(Object object,String name,Object value){
        try {
            Field field=object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getClassFromString(String name){

        try {

            return Class.forName(name);

        } catch (Exception e ){
            e.printStackTrace();
            return null;
        }

    }
}
