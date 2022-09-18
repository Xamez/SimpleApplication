package fr.xamez.simpleapplication.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ReflectionUtils {

    private static String version;

    public static String getVersion() {
        if (version == null) version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];;
        return version;
    }

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class " + className + " not found");
        }
    }

    public static int generateEntityId() {
        final Class<?> entityClazz = getClass("net.minecraft.world.entity.Entity");
        for (Field f : entityClazz.getDeclaredFields())
            if (f.getType().equals(AtomicInteger.class)) {
                f.setAccessible(true);
                try {
                    final AtomicInteger value = (AtomicInteger) f.get(null);
                    value.incrementAndGet();
                    return value.get();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to get entity id");
                }
            }
        return new Random().nextInt() * Integer.MAX_VALUE; // if the field is not found or something goes wrong, return a random value
    }
}
