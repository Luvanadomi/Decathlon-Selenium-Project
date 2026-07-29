package listeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotListener implements ITestListener {

    private static final Path SCREENSHOTS_DIRECTORY = Path.of("target", "screenshots");
    private static final Path REPORT_PATH = Path.of("target", "extent-report", "index.html");
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
    private static final ExtentReports EXTENT = createReport();
    private final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static ExtentReports createReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH.toString());
        reporter.config().setDocumentTitle("Lufthansa Test Report");
        reporter.config().setReportName("Automation Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(EXTENT.createTest(result.getTestClass().getRealClass().getSimpleName()
                + " - " + result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.PASS, "Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.fail(result.getThrowable());
        }
        Object testInstance = result.getInstance();
        if (!(testInstance instanceof BaseTest baseTest)) {
            return;
        }

        WebDriver driver = baseTest.getDriver();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        String fileName = result.getTestClass().getRealClass().getSimpleName()
                + "-" + result.getMethod().getMethodName()
                + "-" + LocalDateTime.now().format(TIMESTAMP) + ".png";
        File destination = SCREENSHOTS_DIRECTORY.resolve(fileName).toFile();

        try {
            Files.createDirectories(SCREENSHOTS_DIRECTORY);
            Files.copy(screenshotDriver.getScreenshotAs(OutputType.FILE).toPath(), destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Failure screenshot saved to: " + destination.getPath());
            if (extentTest != null) {
                extentTest.addScreenCaptureFromPath(destination.getAbsolutePath());
            }
        } catch (IOException | RuntimeException exception) {
            System.err.println("Could not save failure screenshot: " + exception.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.SKIP, "Test skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
    }
}
