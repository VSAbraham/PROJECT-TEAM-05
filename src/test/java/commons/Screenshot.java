package commons;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Screenshot {

    public static void captureScreenshot(WebDriver driver, String fileNamePrefix) {

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = fileNamePrefix + "_" + timestamp + ".png";

        File destFile = new File("screenshots/" + fileName);
        try {

            Files.createDirectories(destFile.getParentFile().toPath());

            Files.copy(srcFile.toPath(), destFile.toPath());

            System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());

        } catch (IOException e) {

            System.out.println("Failed to save screenshot: " + e.getMessage());

        }

    }

}