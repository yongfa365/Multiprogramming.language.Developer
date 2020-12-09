import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

//槽点1：Junit的槽点
//槽点2：要启动站点，没法Mock
public class ApiTest {
    @ParameterizedTest(name = "除法测试：{0}/{1} == {2}")
    @CsvSource(value = {
            " 6 | 2  |   3   ",
            "-6 | -2 |   3   ",
            " 1 | 0  | Error ",
            "   |    | Error ",
    }, delimiterString = "|")
    public void testByApi(Integer a, Integer b, String expected) {

        String result;
        try {
            var url = URI.create("http://localhost:8080/calc/divide/" + a + "/" + b);
            var rs = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder().uri(url).build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            if (rs.statusCode() != 200) {
                throw new RuntimeException();
            }
            result = rs.body();
        } catch (Exception e) {
            result = "Error";
        }

        assertThat(result).isEqualTo(expected);
    }
}
