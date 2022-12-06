package com.telekom.task;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class AppController {

    Logger logger = LoggerFactory.getLogger(AppController.class);

    CSRParser csrParser = new CSRParser();

    @RequestMapping("/")
    public String landingPage(Model model) {
        logger.info("Request received for / ");
        return "index";
    }

    /***
     * CSR file uplaod endpoint
     *
     * @param model
     * @param multipartFile uploaded file
     * @return redirects to csrInfoViewer.html
     * @throws IOException
     */
    @PostMapping("/upload")
    public String fileupload(Model model, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        logger.info("Request received for /upload");
        PKCS10CertificationRequest csr = csrParser.parseCSRFromFile(multipartFile);
        return responseHandler(model, csr);
    }

    /***
     * CSR PEM as text endpoint
     * @param model
     * @param csrString CSR PEM format
     * @return redirects to csrInfoViewer.html
     * @throws IOException
     */

    @PostMapping("/submitCSR")
    public String formSubmit(Model model, @ModelAttribute("csr") String csrString) throws IOException {
        logger.info("Request received for \"/submitCSR\"");
        //remove blanks within CSR String which cause parsing errors
        csrString = csrString.substring(0, 35) + "\n" + csrString.substring(36, csrString.length() - 34).replaceAll(" ", "") + "\n" + csrString.substring(csrString.length() - 33);
        logger.info("Received CSR:\n"+ csrString);
        PKCS10CertificationRequest csr = csrParser.parseCSRFromString(csrString);
        return responseHandler(model, csr);
    }

    /***
     * Prepares response containing parsed CSR information
     * @param model
     * @param csr Parsed {@link PKCS10CertificationRequest} CSR object
     * @return
     */
    String responseHandler(Model model, PKCS10CertificationRequest csr) {
        //Add information map to model
        model.addAttribute("csrInfo", csrInfo(csr));
        logger.info("Response sent with CSR info.");
        return "csrInfoViewer";
    }

    /***
     * creates parsed CSR information in a map
     * @param csr
     * @return information map
     */
    Map<String, Object> csrInfo(PKCS10CertificationRequest csr) {

        Map<String, Object> response = new HashMap<>();
        response.put("Subject", this.csrParser.getSubjectName(csr));
        response.put("Public Key Algorithm", this.csrParser.getPKAlg(csr));
        response.put("Attributes", this.csrParser.getAttributes(csr));
        return response;
    }
}
