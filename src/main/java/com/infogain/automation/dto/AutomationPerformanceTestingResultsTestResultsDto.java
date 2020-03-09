package com.infogain.automation.dto;
import org.apache.jmeter.reporters.AutomationPerformanceTestingSummarizedResults;
import org.json.simple.JSONObject;
public class AutomationPerformanceTestingResultsTestResultsDto extends AutomationOutput {
    AutomationPerformanceTestingResultsTestResults detailedResults;
    AutomationPerformanceTestingSummarizedResults summary;
    JSONObject resultStats;
    String htmlReportPath;

    public AutomationPerformanceTestingResultsTestResults getDetailedResults() {
        return detailedResults;
    }

    public void setDetailedResults(AutomationPerformanceTestingResultsTestResults detailedResults) {
        this.detailedResults = detailedResults;
    }

    public AutomationPerformanceTestingSummarizedResults getSummary() {
        return summary;
    }

    public void setSummary(AutomationPerformanceTestingSummarizedResults summary) {
        this.summary = summary;
    }

    public JSONObject getResultStats() {
        return resultStats;
    }

    public void setResultStats(JSONObject resultStats) {
        this.resultStats = resultStats;
    }

    public String getHtmlReportPath() {
        return htmlReportPath;
    }

    public void setHtmlReportPath(String htmlReportPath) {
        this.htmlReportPath = htmlReportPath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingResultsTestResultsDto [");
        if (detailedResults != null) {
            builder.append("detailedResults=").append(detailedResults).append(", ");
        }
        if (summary != null) {
            builder.append("summary=").append(summary).append(", ");
        }
        if (resultStats != null) {
            builder.append("resultStats=").append(resultStats).append(", ");
        }
        if (htmlReportPath != null) {
            builder.append("htmlReportPath=").append(htmlReportPath);
        }
        builder.append("]");
        return builder.toString();
    }

   

}
