package com.telekom.task;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Controller
public class AppController {

    CSRParser csrParser = new CSRParser();

    @RequestMapping("/")
    public String landingPage(Model model){
        return "index";
    }
    @PostMapping("/upload")
    public ResponseEntity<Object> fileupload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        PKCS10CertificationRequest csr = csrParser.parseCSRFromFile(multipartFile);
        return csrInfo(csr);
    }

    @PostMapping("/submitCSR")
    public ResponseEntity<Object> formSubmit(@RequestParam("csr") String csrString) throws IOException {
        PKCS10CertificationRequest csr = csrParser.parseCSRFromString(csrString);
        return csrInfo(csr);

    }

    ResponseEntity<Object> csrInfo(PKCS10CertificationRequest csr){

        Map<String, Object> response = new HashMap<>();
        response.put("Subject", csr.getSubject().toString());
        response.put("Attributes", Arrays.toString(csr.getAttributes()));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
