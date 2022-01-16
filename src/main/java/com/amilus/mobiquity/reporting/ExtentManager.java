package com.amilus.mobiquity.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager
{
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./reports/TestAutomation-Results-Report.html");
        reporter.config().setReportName("Mobiquity API Test Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
