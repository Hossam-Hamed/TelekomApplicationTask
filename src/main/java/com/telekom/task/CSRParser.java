package com.telekom.task;

import org.bouncycastle.asn1.ASN1Set;

import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.DefaultAlgorithmNameFinder;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static java.util.Collections.replaceAll;

public class CSRParser {

    ArrayList<PKCS10CertificationRequest> parsedCSRs;

    public CSRParser() {
        parsedCSRs = new ArrayList<>();
    }

    // Parse CSR from string
    public PKCS10CertificationRequest parseCSRFromString(String pemCSR) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(pemCSR.getBytes());
        Reader bufferedReader = new BufferedReader(new InputStreamReader(in));
        PEMParser pemReader = new PEMParser(bufferedReader);
        PKCS10CertificationRequest parsedCSR = (PKCS10CertificationRequest) pemReader.readObject();
        parsedCSRs.add(parsedCSR);
        return parsedCSR;
    }
    // Parse CSR from Multipartfile
    public PKCS10CertificationRequest parseCSRFromFile(MultipartFile multipartFile) throws IOException {
        Reader bufferedReader = new InputStreamReader(multipartFile.getInputStream());
        PEMParser pemReader = new PEMParser(bufferedReader);
        PKCS10CertificationRequest parsedCSR = (PKCS10CertificationRequest) pemReader.readObject();
        parsedCSRs.add(parsedCSR);
        return parsedCSR;
    }

    public String getSubjectName(PKCS10CertificationRequest csr) {
        return csr.getSubject().toString();


    }

    public String getAttributes(PKCS10CertificationRequest csr) {
        Attribute[] attributes = csr.getAttributes();
        //TODO find SAN in attributes
        return Arrays.toString(attributes);
    }

    public String getPKAlg(PKCS10CertificationRequest csr) {
        DefaultAlgorithmNameFinder defaultAlgorithmNameFinder = new DefaultAlgorithmNameFinder();
        AlgorithmIdentifier algorithmIdentifier = csr.getSubjectPublicKeyInfo().getAlgorithm();
        return defaultAlgorithmNameFinder.getAlgorithmName(algorithmIdentifier);
    }

    public static void main(String[] args) throws IOException {
        CSRParser csrParser = new CSRParser();
        String sampleCSR =
                "-----BEGIN CERTIFICATE REQUEST-----\n" +
                        "MIICvDCCAaQCAQAwdzELMAkGA1UEBhMCVVMxDTALBgNVBAgMBFV0YWgxDzANBgNV\n" +
                        "BAcMBkxpbmRvbjEWMBQGA1UECgwNRGlnaUNlcnQgSW5jLjERMA8GA1UECwwIRGln\n" +
                        "aUNlcnQxHTAbBgNVBAMMFGV4YW1wbGUuZGlnaWNlcnQuY29tMIIBIjANBgkqhkiG\n" +
                        "9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8+To7d+2kPWeBv/orU3LVbJwDrSQbeKamCmo\n" +
                        "wp5bqDxIwV20zqRb7APUOKYoVEFFOEQs6T6gImnIolhbiH6m4zgZ/CPvWBOkZc+c\n" +
                        "1Po2EmvBz+AD5sBdT5kzGQA6NbWyZGldxRthNLOs1efOhdnWFuhI162qmcflgpiI\n" +
                        "WDuwq4C9f+YkeJhNn9dF5+owm8cOQmDrV8NNdiTqin8q3qYAHHJRW28glJUCZkTZ\n" +
                        "wIaSR6crBQ8TbYNE0dc+Caa3DOIkz1EOsHWzTx+n0zKfqcbgXi4DJx+C1bjptYPR\n" +
                        "BPZL8DAeWuA8ebudVT44yEp82G96/Ggcf7F33xMxe0yc+Xa6owIDAQABoAAwDQYJ\n" +
                        "KoZIhvcNAQEFBQADggEBAB0kcrFccSmFDmxox0Ne01UIqSsDqHgL+XmHTXJwre6D\n" +
                        "hJSZwbvEtOK0G3+dr4Fs11WuUNt5qcLsx5a8uk4G6AKHMzuhLsJ7XZjgmQXGECpY\n" +
                        "Q4mC3yT3ZoCGpIXbw+iP3lmEEXgaQL0Tx5LFl/okKbKYwIqNiyKWOMj7ZR/wxWg/\n" +
                        "ZDGRs55xuoeLDJ/ZRFf9bI+IaCUd1YrfYcHIl3G87Av+r49YVwqRDT0VDV7uLgqn\n" +
                        "29XI1PpVUNCPQGn9p/eX6Qo7vpDaPybRtA2R7XLKjQaF9oXWeCUqy1hvJac9QFO2\n" +
                        "97Ob1alpHPoZ7mWiEuJwjBPii6a9M9G30nUo39lBi1w=\n"
//                ;
                        +
                        "-----END CERTIFICATE REQUEST-----";

        PKCS10CertificationRequest parsedCsr = csrParser.parseCSRFromString(sampleCSR);
        System.out.println(csrParser.getSubjectName(parsedCsr));
        System.out.println(csrParser.getPKAlg(parsedCsr));
        System.out.println(csrParser.getAttributes(parsedCsr));
    }
}
