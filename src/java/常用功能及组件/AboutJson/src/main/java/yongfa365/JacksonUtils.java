package yongfa365;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class JacksonUtils {
    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal() {
        protected SimpleDateFormat initialValue() {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssz");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//2019-01-02T13:30:27.631+08:00


            return simpleDateFormat;
        }
    };
    //把Object转换成Json字符串的mapper的配置
    private static ObjectMapper write = new ObjectMapper() {
        {
            setTimeZone(TimeZone.getTimeZone("GMT+8"));
            setDateFormat(local.get());
            configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        }
    };
    //把Object转换成Json字符串的mapper的配置
    private static ObjectMapper writeNoIndent = new ObjectMapper() {
        {
            setDateFormat(local.get());
            setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
    };
    //把JSON字符串转换成Object的mapper的配置
    private static ObjectMapper read = new ObjectMapper() {
        {
            setDateFormat(local.get());

            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        }
    };

    /**
     * Object 转 Json字符串
     *
     * @param object 元素类型
     * @return
     */
    public static String toJson(Object object) {
        return toJson(object, false);
    }

    /**
     * Object 转 Json字符串
     *
     * @param object 元素类型
     * @return
     */
    public static String toJson(Object object, boolean indent) {
        try {
            if (object == null) {
                return null;
            }
            if (object instanceof String) {
                return (String) object;
            }
            if (indent) {
                return write.writeValueAsString(object);
            } else {
                return writeNoIndent.writeValueAsString(object);
            }
        } catch (Exception e) {
            throw new RuntimeException("对象转JSON出错", e);
        }
    }

    /**
     * JSON 转Object
     *
     * @param json json字符串
     * @param clzz 元素类型
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, Class<T> clzz) {
        try {
            if (json == null) {return null;}
            return read.readValue(json, clzz);
        } catch (Exception e) {
            throw new RuntimeException("JSON转对象出错：" + json, e);
        }
    }

    /**
     * JSON 转Object
     *
     * @param object json字符串
     * @param clzz   元素类型
     * @param <T>
     * @return
     */
    public static <T> T toBean(Object object, Class<T> clzz) {
        String json = null;
        try {
            if (object == null) {return null;}
            json = toJson(object);
            return read.readValue(json, clzz);
        } catch (Exception e) {
            throw new RuntimeException("JSON转对象出错：" + json, e);
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    @SafeVarargs
    public static <T> T toBean(Object jsonStr, Class<T> collectionClass, Class<T>... elementClasses) {
        try {
            JavaType javaType = read.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return read.readValue(toJson(jsonStr), javaType);
        } catch (Exception e) {
            throw new RuntimeException("JSON转对象出错：" + jsonStr, e);
        }

    }


    /**
     * 获取泛型的Collection Type
     *
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    @SafeVarargs
    public static <T extends Collection> T toBean(String jsonStr, Class<T> collectionClass, Class<?>... elementClasses) {
        try {
            if (jsonStr == null){ return null;}
            if (elementClasses[0] != null && elementClasses[0].isAssignableFrom(Map.class)) {
                JavaType map = read.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
                return read.readValue(jsonStr, read.getTypeFactory().constructCollectionType(collectionClass, map));
            }
            JavaType javaType = read.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return read.readValue(jsonStr, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Field[] getFields(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (obj.getClass().getSuperclass() != null) {
            Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
            Field[] result = Arrays.copyOf(declaredFields, declaredFields.length + superFields.length);
            System.arraycopy(superFields, 0, result, declaredFields.length, superFields.length);
            return result;
        }
        return declaredFields;
    }

    /**
     * 转带泛型的Bean
     *
     * @param json
     * @param beanClass
     * @param genericClass
     * @param <T>
     * @return
     */
    public static <T> T toGenericBean(String json, Class<T> beanClass, Class<?> genericClass) {
        try {
            JavaType bean = read.getTypeFactory().constructParametrizedType(beanClass, beanClass, genericClass);
            return read.readValue(json, read.getTypeFactory().constructType(bean));
            //return read.readValue(json, bean);

            //return read.readValue(json,new TypeReference<CommonRS<OrderDetailRS>>(){});
        } catch (IOException e) {
            throw new RuntimeException("JSON转对象出错：" + json, e);
        }

    }

    public static <T> T toCommonrsListData(String json, TypeReference<?> typeReference) throws IOException {

        return read.readValue(json, typeReference);
    }
}
