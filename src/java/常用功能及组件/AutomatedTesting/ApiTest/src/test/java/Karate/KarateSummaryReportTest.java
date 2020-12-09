package Karate;

import com.intuit.karate.Runner;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.generators.FeatureReportPage;
import net.masterthought.cucumber.json.Feature;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Disabled
public class KarateSummaryReportTest {

    @Test
    public void runFeatureAndGenReport() {
        generateReport(true);
    }

    @Disabled
    @Test
    public void noRunFeatureAndGenReport() {
        generateReport(false);
    }

    public void generateReport(boolean isNeedRun) {
        //System.setProperty("karate.env", "test");
        var karateEnv = System.getProperty("karate.env");
        var sunJavaCommand = System.getProperty("sun.java.command");
        var isNeedGenReport = karateEnv != null || (sunJavaCommand != null && sunJavaCommand.contains(this.getClass().getName()));
        if (isNeedGenReport) {
            if (isNeedRun) {
                var results = Runner.path("classpath:Calc.Karate.feature").tags("~@ignore").parallel(1);
                generateReport(results.getReportDir());
            } else {
                generateReport("target\\surefire-reports");
            }
        }
    }

    private void generateReport(String karateOutputPath) {
        var jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        var jsonPaths = jsonFiles.stream().map(p -> p.getAbsolutePath()).collect(Collectors.toList());

        var config = new Configuration(new File("target", "surefire-reports"), "ApiTest呀");
        var reportBuilder = new ReportBuilder(jsonPaths, config);

        // 牛逼吧，报表组件不合我意，干他！
        new MockUp<Feature>() {
            @Mock
            public String getName(Invocation invocation) {
                var thisObj = (Feature) invocation.getInvokedInstance();
                var result = thisObj.getId();
                return result;
            }
        };
        new MockUp<FeatureReportPage>() {

            @Mock
            private void generatePage(Invocation invocation) {
                invocation.proceed();
                try {
                    var thisObj = (FeatureReportPage) invocation.getInvokedInstance();
                    var feature = (Feature) ReflectionTestUtils.getField(thisObj, "feature");

                    var path = Path.of("target", "surefire-reports", "cucumber-html-reports", thisObj.getWebPage());
                    var result = Files.readString(path).replace("<h2>Feature Report</h2>", "<h2>Feature Report：" + feature.getUri() + "</h2>");
                    Files.write(path, result.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        reportBuilder.generateReports();
    }

}

