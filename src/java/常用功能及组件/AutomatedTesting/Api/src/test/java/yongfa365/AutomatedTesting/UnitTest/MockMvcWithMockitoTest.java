package yongfa365.AutomatedTesting.UnitTest;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import yongfa365.AutomatedTesting.CalcService;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 亮点：MockMvc美妙之处在于：用单元测试的方式测Api，同时可以Mock Api里用到的类，如各种Service
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-mocking-beans
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcWithMockitoTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    CalcService calcService;

    @PostConstruct
    void before() {
        // https://github.com/spring-projects/spring-boot/issues/7321#issuecomment-261343803
        mockMvc.getDispatcherServlet()
                .setDetectAllHandlerExceptionResolvers(true);
    }

    @ParameterizedTest(name = "除法测试：{0}/{1} == {2}")
    @CsvSource(value = {
            " 6 | 2  |   3   ",
            "-6 | -2 |   3   ",
            " 1 | 0  | Error ",
    }, delimiterString = "|")
    public void testByMockMvc(Integer a, Integer b, String expected) {
        // 可以mock CalcController的成员CalcService
        doReturn(-110).when(calcService).divide(anyInt(), anyInt());

        String result;
        try {
            // 这个url没有domain不是真正的调用api
            var rs = mockMvc.perform(get("/calc/divide/" + a + "/" + b))
                    .andDo(print())
                    .andReturn()
                    .getResponse();
            if (rs.getStatus() != 200) {
                throw new RuntimeException();
            }
            result = rs.getContentAsString();
        } catch (Exception e) {
            result = "Error";
        }

        assertThat(result).isEqualTo(expected);
    }
}
