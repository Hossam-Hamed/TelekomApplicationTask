package com.telekom.task;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String landingPage(Model model) {
        return "index";
    }

    @PostMapping("/upload")
    public String fileupload(Model model, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        PKCS10CertificationRequest csr = csrParser.parseCSRFromFile(multipartFile);
        return responseHandler(model, csr);
    }

    @PostMapping("/submitCSR")
    public String formSubmit(Model model, @ModelAttribute("csr") String csrString) throws IOException {
        //remove blanks
        csrString = csrString.substring(0,35)+"\n"+csrString.substring(36,csrString.length()-34).replaceAll(" ","")+"\n"+csrString.substring(csrString.length()-33);
        PKCS10CertificationRequest csr = csrParser.parseCSRFromString(csrString);
        return responseHandler(model, csr);

    }

    String responseHandler(Model model, PKCS10CertificationRequest csr) {

        model.addAllAttributes(csrInfo(csr));
        return "csrInfoViewer";
    }

    Map<String, Object> csrInfo(PKCS10CertificationRequest csr) {

        Map<String, Object> response = new HashMap<>();
        response.put("Subject", csr.getSubject().toString());
        response.put("Attributes", Arrays.toString(csr.getAttributes()));
        return response;
    }
}
