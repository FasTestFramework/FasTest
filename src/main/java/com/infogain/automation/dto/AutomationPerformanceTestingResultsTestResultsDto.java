package com.infogain.automation.dto;
import org.apache.jmeter.reporters.AutomationPerformanceTestingSummarizedResults;
import org.json.simple.JSONObject;
public class AutomationPerformanceTestingResultsTestResultsDto extends AutomationOutput {
    AutomationPerformanceTestingSummarizedResults summary;
    JSONObject resultStats;
    String htmlReportPath;
    String csvReportPath;
    String xmlReportPath;
    String jmxReportPath;

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

    public String getCsvReportPath() {
		return csvReportPath;
	}

	public void setCsvReportPath(String csvReportPath) {
		this.csvReportPath = csvReportPath;
	}

	public String getXmlReportPath() {
		return xmlReportPath;
	}

	public void setXmlReportPath(String xmlReportPath) {
		this.xmlReportPath = xmlReportPath;
	}

	public String getJmxReportPath() {
		return jmxReportPath;
	}

	public void setJmxReportPath(String jmxReportPath) {
		this.jmxReportPath = jmxReportPath;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingResultsTestResultsDto [");
        if (summary != null) {
            builder.append("summary=").append(summary).append(", ");
        }
        if (resultStats != null) {
            builder.append("resultStats=").append(resultStats).append(", ");
        }
        if (htmlReportPath != null) {
            builder.append("htmlReportPath=").append(htmlReportPath);
        }
        if (csvReportPath != null) {
            builder.append("csvReportPath=").append(csvReportPath);
        }
        if (xmlReportPath != null) {
            builder.append("xmlReportPath=").append(xmlReportPath);
        }
        if (jmxReportPath != null) {
            builder.append("jmxReportPath=").append(jmxReportPath);
        }
        builder.append("]");
        return builder.toString();
    }

}
