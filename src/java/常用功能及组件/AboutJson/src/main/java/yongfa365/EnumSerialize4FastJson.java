package yongfa365;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import yongfa365.model.BaseEnum;

import java.lang.reflect.Type;

public class EnumSerialize4FastJson implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
        SerializeWriter out = jsonSerializer.getWriter();
        if (o == null) {
            jsonSerializer.getWriter().writeNull();
            return;
        }

        if (o instanceof BaseEnum) {
            BaseEnum baseEnum = (BaseEnum) o;
            out.write("" + baseEnum.getValue() + "");
        } else {
            out.writeEnum((Enum<?>) o);//用默认的tostring
        }
    }
}
