package yongfa365.AboutEasyExcel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebTest {

    @GetMapping(value = "/")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment;filename=" + ExcelHelper.genFileName());
        var lstData = ExcelHelper.genPersons(10000);
        ExcelHelper.Export(lstData, response.getOutputStream());
    }
}
