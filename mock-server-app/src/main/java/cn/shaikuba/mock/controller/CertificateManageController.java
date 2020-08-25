package cn.shaikuba.mock.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("mock/manage")
@CrossOrigin(origins = "*")
public class CertificateManageController {

    private static final String CERT_BASE_PATH = "securities/";

    @GetMapping(value = "/cert", params = "filename")
    public void getServerCert(@RequestParam(value = "filename") String filename, HttpServletResponse httpResponse) throws IOException {
        String certFilepath = CERT_BASE_PATH + filename;

        httpResponse.setStatus(200);
        httpResponse.setContentType("multipart/form-data");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setHeader("Content-Disposition", "attachment;fileName=" + filename);
        ServletOutputStream outputStream = httpResponse.getOutputStream();
        outputStream.write(toByteArray(certFilepath));
        outputStream.flush();

    }

    private static byte[] toByteArray(String classpath) {
        try {
            return IOUtils.toByteArray(fileInputStream(classpath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new byte[0];
        }
    }

    private static InputStream fileInputStream(String classpath) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath);
    }

}
