package yongfa365;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import yongfa365.model.Children;
import yongfa365.model.Father;
import yongfa365.model.GenderEnum;
import yongfa365.model.HobbyEnum;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class JsonUtilsApplication {

    public static void main(String[] args) throws IOException {
        //SpringApplication.run(JsonUtilsApplication.class, args);
        fastJsonFatherSonSerilize();
        fastjsonFatherSonDeserilize();
        jacksonFatherSonSerilize();
        jacksonFatherSonDeserilize();
    }


    private static void fastJsonFatherSonSerilize() {
        Father father = new Father();
        Children children = new Children();
        children.setAge(18);
        children.setName("eee");
        children.setMyFather(father);
        children.setGender(GenderEnum.male);
        List<HobbyEnum> hobbys = new ArrayList<>();
        hobbys.add(HobbyEnum.eat);
        children.setHobbys(hobbys);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fgfdg", "dfgdfg");
        children.setHashMap(hashMap);


        father.setAge(40);
        father.setName("Bob");
        father.setMyChildren(children);
        father.setBirthday(LocalDateTime.of(1979, 1, 1, 1, 1));

        String jsonStr = JSON.toJSONString(father, false);

        //{"age":40,"birthday":"1979-01-01T01:01:00","mySon":{"age":18,"myFather":{"$ref":".."},"name":"eee"},"name":"Bob"}默认就支持这种循环引用的了  maven仓库里面最新的版本
        System.out.println(jsonStr);
    }

    private static void fastjsonFatherSonDeserilize() {
        // todo：不显示阻断循环引用时 有个 ‘ref’ 这种反序列化出来 父子级关系很深 不干净啊  没设置好？？？ 不是自动支持了？？？
        String jsonStr = "{\"age\":40,\"birthday\":\"1979-01-01T01:01:00\",\"myChildren\":{\"age\":18,\"gender\":1,\"myFather\":{\"$ref\":\"..\"},\"name\":\"eee\"},\"name\":\"Bob\"}";

        jsonStr = "{\"age\":40,\"birthday\":\"1979-01-01T01:01:00\",\"myChildren\":{\"age\":18,\"gender\":1,\"hashMap\":{\"fgfdg\":\"dfgdfg\"},\"hobbys\":[\"eat\"],\"name\":\"eee\"},\"name\":\"Bob\"}";
        Father father = JSON.parseObject(jsonStr, Father.class); //这种直接parseobject的方式  @jsonfield 里面指定的序列化 反序列化才会起作用


        System.out.println(father.getBirthday());
    }

    //localdatetime 需要加序列化 反序列化 模块  https://blog.csdn.net/junlovejava/article/details/78112240
    private static void jacksonFatherSonSerilize() throws JsonProcessingException {
        Father father = new Father();
        Children children = new Children();
        children.setAge(18);
        children.setName("eee");
        children.setMyFather(father);
        children.setGender(GenderEnum.female);
        List<HobbyEnum> hobbys = new ArrayList<>();
        hobbys.add(HobbyEnum.drink);
        children.setHobbys(hobbys);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fgfdg", "dfgdfg");
        children.setHashMap(hashMap);

        father.setAge(40);
        father.setName("Bob");
        father.setMyChildren(children);
        father.setBirthday(LocalDateTime.of(1979, 1, 1, 1, 1));

        ObjectMapper writer = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        writer.registerModule(javaTimeModule);
        writer.setSerializationInclusion(JsonInclude.Include.NON_NULL);//忽略空字段

        String jsonStr = writer.writeValueAsString(father);

//{"mySon":{"name":"eee","age":18},"name":"Bob","age":40,"birthday":"1979-01-01 01:01:00"} 需要 在对应字段上加上注解 @JsonBackReference
        System.out.println(jsonStr);
    }

    private static void jacksonFatherSonDeserilize() throws IOException {
        String jsonStr = "{\"myChildren\":{\"name\":\"eee\",\"age\":18,\"gender\":2,\"hobbys\":[1],\"hashMap\":{\"fgfdg\":\"dfgdfg\"}},\"name\":\"Bob\",\"age\":40,\"birthday\":\"1979-01-01 01:01:00\"}";

        ObjectMapper reader = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        reader.registerModule(javaTimeModule);

        Father father = reader.readValue(jsonStr, Father.class);
        System.out.println(father.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
