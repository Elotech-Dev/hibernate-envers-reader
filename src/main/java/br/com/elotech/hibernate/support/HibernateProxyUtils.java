package br.com.elotech.hibernate.support;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import lombok.SneakyThrows;

public final class HibernateProxyUtils {

    private HibernateProxyUtils() {
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T initializeProxys(T obj) {

        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());

        for (Field field : fields) {

            field.setAccessible(true);

            Object value = field.get(obj);

            if (value != null) {

                internalInitProxys(obj, field, value);

            }

        }

        return (T) unProxy(obj);
    }

    private static <T> void internalInitProxys(T obj, Field field, Object value) throws IllegalAccessException {

        if (!Hibernate.isInitialized(value)) {

            Hibernate.initialize(value);

            field.set(obj, unProxy(value));

        } else {

            List<Class<?>> interfaces = Arrays.asList(value.getClass().getInterfaces());

            if (interfaces.contains(Collection.class)
                    || interfaces.contains(List.class)) {

                Collection<?> collection = (Collection<?>) value;

                for (Object object : collection) {

                    initializeProxys(object);
                }

            } else if (value instanceof HibernateProxy) {

                field.set(obj, unProxy(value));

            } else if (field.isAnnotationPresent(Embedded.class)) {

                initializeProxys(value);
            }

        }
    }

    private static <T> Object unProxy(Object value) throws IllegalAccessException {
        if (value instanceof HibernateProxy) {

            HibernateProxy proxy = (HibernateProxy) value;

            Object valueWithouProxy = proxy.getHibernateLazyInitializer().getImplementation();

            valueWithouProxy = initializeProxys(valueWithouProxy);

            return valueWithouProxy;

        }
        return value;
    }

}
