package yongfa365.AutomatedTesting.UnitTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import yongfa365.AutomatedTesting.CalcService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 亮点：Junit5将参数化测试化繁为简、让数据驱动测试变成再简单不过的事，
 *       这也减少了测试用例代码量及维护成本。
 *
 * 槽点：数据源里的数据对其要用个idea插件：String Manipulation
 *       效果还凑合，没Cucumber的feature里支持的好。
 */
public class JunitTest {

    CalcService calcService = new CalcService();

    @ParameterizedTest(name = "除法测试：{0}/{1} == {2}")
    @CsvSource(value = {
            " 6 | 2  |   3   ",
            "-6 | -2 |   3   ",
            " 1 | 0  | Error ",
            "   |    | Error ",
    }, delimiterString = "|")
    public void testDivide(Integer a, Integer b, String expected) {
        String result;
        try {
            var temp = calcService.divide(a, b);
            result = temp.toString();
        } catch (Exception e) {
            result = "Error";
        }

        assertThat(result).isEqualTo(expected);
    }


    @ParameterizedTest(name = "加法测试：{0}+{1} == {2}")
    @CsvSource(value = {
            " 6  | 2   |   8   ",
            "-6  | -2  |   -8  ",
            "-6  | 2   |   -4  ",
            "    |     | Error ",
    }, delimiterString = "|")
    public void testAdd(Integer a, Integer b, String expected) {
        String result;
        try {
            var temp = calcService.add(a, b);
            result = temp.toString();
        } catch (Exception e) {
            result = "Error";
        }

        assertThat(result).isEqualTo(expected);
    }


}
