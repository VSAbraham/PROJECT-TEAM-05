package commons;
 
import org.openqa.selenium.OutputType;

import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;

import org.apache.commons.io.FileUtils;
 
import java.io.File;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
 
public class ScreenshotUntil {
 
    public static void takeScreenshot(WebDriver driver, String fileName) {

        if (driver == null) {

            System.out.println("Driver is not initialized. Cannot take screenshot.");

            return;

        }
 
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String folderPath = "/PROJECT-TEAM-05/screenshots";

        File folder = new File(folderPath);

        if (!folder.exists()) folder.mkdirs();
 
        File destFile = new File(folderPath + "\\" + fileName + "_" + timestamp + ".png");

        try {

            FileUtils.copyFile(srcFile, destFile);

            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());

        } catch (IOException e) {

            System.out.println("Failed to save screenshot: " + e.getMessage());

        }

    }

}

 