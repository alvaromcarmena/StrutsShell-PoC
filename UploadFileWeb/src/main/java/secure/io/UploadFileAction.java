package secure.io;


import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class UploadFileAction extends ActionSupport {
    private File upload;
    private String uploadFileName;
    private String uploadContentType;

    public String execute() throws Exception {
        if (uploadFileName != null) {
            try {
                String uploadDirectory = "/tmp/uploads/";
                File destFile = new File(uploadDirectory, uploadFileName);
                
                System.out.println("uploadFileName: " + uploadFileName);
                System.out.println("destination file path: " + destFile.getPath());
                
                //Vulnerability fix
//                if (!destFile.getCanonicalPath().startsWith(uploadDirectory)){
//                	System.out.println("Canonical path of the file: " + destFile.getCanonicalPath() + " does not match upload path: " + uploadDirectory);
//                	throw new Exception ("Attempt of path traversal detected!");
//                }
                
                FileUtils.copyFile(upload, destFile);
                
                System.out.println("File uploaded successfully to: " + destFile.getCanonicalPath());
                addActionMessage("File uploaded successfully");

                return SUCCESS;
            } catch (Exception e) {
                addActionError(e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
        } else {
            return INPUT;
        }
     }

    // Getters and setters
    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }


}
