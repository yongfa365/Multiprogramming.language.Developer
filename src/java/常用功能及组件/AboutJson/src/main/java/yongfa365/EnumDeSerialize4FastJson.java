package yongfa365;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import yongfa365.model.BaseEnum;

import java.lang.reflect.Type;

public class EnumDeSerialize4FastJson implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        final JSONLexer lexer = defaultJSONParser.lexer;
        final int token = lexer.token();
        Class cls = (Class) type;
        Object[] enumConstants = cls.getEnumConstants();
        if (BaseEnum.class.isAssignableFrom(cls)) {
            for (Object enumConstant : enumConstants) {
                BaseEnum baseEnum = (BaseEnum) enumConstant;
                Object enumCodeObject = baseEnum.getValue();
                int enumCode = Integer.parseInt(enumCodeObject.toString());
                if (lexer.intValue() == enumCode) {
                    return (T) baseEnum;
                }
            }
        } else {
            //没实现baseenum接口的 默认的按名字或者按ordinal
            if (token == JSONToken.LITERAL_INT) {
                int intValue = lexer.intValue();
                lexer.nextToken(JSONToken.COMMA);
                if (intValue < 0 || intValue > enumConstants.length) {
                    throw new JSONException(String.format("parse enum %s error, value : %s", cls.getName(), intValue));
                }
                return (T) enumConstants[intValue];
            } else if (token == JSONToken.LITERAL_STRING) {
                return (T) Enum.valueOf(cls, lexer.stringVal());
            }
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
