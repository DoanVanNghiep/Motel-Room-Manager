package fita.vnua.edu.vn.MotelRoomManager.Until;

import jakarta.servlet.http.Part;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUntil {
    public static String getTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy hh_mm");
        return sdf.format(new Date());
    }

    public static String extracFileExtension(Part part) {
        // form-data; name = "file"; filename = "";
        String contentDisp = part.getHeader("content-disposition");
        int indexOfDot = contentDisp.lastIndexOf(".");
        return contentDisp.substring(indexOfDot, contentDisp.length() - 1); // return .jpg
    }
    public static File getFolderUpload(String appPath, String folderName) {
        // user.dir: thu muc ung dung Web hien tai
        File folderUpload = new File(appPath + File.separator + folderName);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
}

