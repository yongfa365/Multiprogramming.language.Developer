package yongfa365.AutomatedTesting.UnitTest;

import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import yongfa365.AutomatedTesting.CalcService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 亮点:Cucumber造出了BDD，目标是让需求、开发、测试人员都能看懂并编辑，共同完成可测试文档。
 *      原生的写法配合代码生成的报表简单直观，测试人员写feature、开发人员写对应的实现。
 *
 * 槽点1：Steps只能写在构造函数里，不能写在别的方法里，所以场景也不能隔离到不同的方法里。
 *       更适合于一个api只放一个feature文件里测试的情形。 如：一个Controller有10个方法、
 *       对应10个api，就要创建10个feature文件；而单元测试通常一个测试文件就测完了这10个方法。
 * 槽点2：所有*.feature里相同名字的Step、只会命中所有代码中的一个，如果命中多个则在IDE右击feature文件会报错。
 * 槽点3：没法在Scenario Outline里定义null，只能用String接收，然后自行解析
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:Calc.feature",
        plugin = {
                "pretty",
                "html:target/Cucumber.Calc.html",
                "json:target/Cucumber.Calc.json"
        }
)
public class CucumberTest implements En {
    CalcService calcService;
    String result;

    public CucumberTest() {

        Given("打开计算器", () -> {
            calcService = new CalcService();
        });
        When("^输入(.*)除(.*)，并计算$", (String a, String b) -> {
            try {
                var temp = calcService.divide(parseInt(a), parseInt(b));
                result = temp.toString();
            } catch (Exception e) {
                result = "Error";
            }
        });
        When("^输入(.*)加(.*)，并计算$", (String a, String b) -> {
            try {
                var temp = calcService.add(parseInt(a), parseInt(b));
                result = temp.toString();
            } catch (Exception e) {
                result = "Error";
            }
        });
        Then("显示结果为:{word}", (String expected) -> {
            assertThat(result).isEqualTo(expected);
        });
    }

    private Integer parseInt(String input) {
        return input.equals("null") ? null : Integer.parseInt(input);
    }
}
