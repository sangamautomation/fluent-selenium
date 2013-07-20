package org.seleniumhq.selenium.fluent.monitors;

import com.codahale.metrics.ConsoleReporter;
import org.junit.Test;
import org.junit.runners.ParentRunner;
import org.seleniumhq.selenium.fluent.Monitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CodehaleMetricsMonitorTest {

    @Test
    public void metrics_hooked_up_to_monitor() {

        CodehaleMetricsMonitor cmm = new CodehaleMetricsMonitor("org.junit.");
        cmm.addClass(ParentRunner.class);
        Monitor.Timer timer = cmm.start("div().span()");
        try {
            Thread.sleep(999);
        } catch (InterruptedException e) {
        }
        timer.end();

        // Capture Stats ...

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final ConsoleReporter reporter = ConsoleReporter.forRegistry(cmm.getMetrics())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.SECONDS)
                .outputTo(new PrintStream(baos))
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
        }

        reporter.stop();

        // ... for assertion

        String result = baos.toString().replace("= 0.48 calls", "= 0.50 calls").replace("= 0.49 calls", "= 0.50 calls");
        result = result.substring(result.indexOf("-- Timers"));
        assertEquals(
                "-- Timers ----------------------------------------------------------------------\n" +
                        "runners.ParentRunner.runLeaf:div().span()\n" +
                        "             count = 1\n" +
                        "         mean rate = 0.50 calls/second\n" +
                        "     1-minute rate = 0.00 calls/second\n" +
                        "     5-minute rate = 0.00 calls/second\n" +
                        "    15-minute rate = 0.00 calls/second\n" +
                        "               min = 1.00 seconds\n" +
                        "               max = 1.00 seconds\n" +
                        "              mean = 1.00 seconds\n" +
                        "            stddev = 0.00 seconds\n" +
                        "            median = 1.00 seconds\n" +
                        "              75% <= 1.00 seconds\n" +
                        "              95% <= 1.00 seconds\n" +
                        "              98% <= 1.00 seconds\n" +
                        "              99% <= 1.00 seconds\n" +
                        "            99.9% <= 1.00 seconds\n\n\n", result);

    }

    @Test
    public void metrics_hooked_up_to_monitor_and_can_infer_classnames() {

        CodehaleMetricsMonitor cmm = new CodehaleMetricsMonitor("org.seleniumhq.selenium.fluent.monitors.CodehaleMetricsMonitor");
        Monitor.Timer timer = cmm.start("div().span()");
        try {
            Thread.sleep(999);
        } catch (InterruptedException e) {
        }
        timer.end();

        // Capture Stats ...

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final ConsoleReporter reporter = ConsoleReporter.forRegistry(cmm.getMetrics())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.SECONDS)
                .outputTo(new PrintStream(baos))
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
        }

        reporter.stop();

        // ... for assertion

        String result = baos.toString().replace("= 0.48 calls", "= 0.50 calls").replace("= 0.49 calls", "= 0.50 calls");
        result = result.substring(result.indexOf("-- Timers"));
        assertEquals(
                "-- Timers ----------------------------------------------------------------------\n" +
                        "Test.metrics_hooked_up_to_monitor_and_can_infer_classnames:div().span()\n" +
                        "             count = 1\n" +
                        "         mean rate = 0.50 calls/second\n" +
                        "     1-minute rate = 0.00 calls/second\n" +
                        "     5-minute rate = 0.00 calls/second\n" +
                        "    15-minute rate = 0.00 calls/second\n" +
                        "               min = 1.00 seconds\n" +
                        "               max = 1.00 seconds\n" +
                        "              mean = 1.00 seconds\n" +
                        "            stddev = 0.00 seconds\n" +
                        "            median = 1.00 seconds\n" +
                        "              75% <= 1.00 seconds\n" +
                        "              95% <= 1.00 seconds\n" +
                        "              98% <= 1.00 seconds\n" +
                        "              99% <= 1.00 seconds\n" +
                        "            99.9% <= 1.00 seconds\n\n\n", result);

    }
}
