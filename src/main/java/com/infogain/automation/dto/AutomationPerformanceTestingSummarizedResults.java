package com.infogain.automation.dto;

public class AutomationPerformanceTestingSummarizedResults {
    String elapsedTime;
    String hitRate;
    String average;
    String min;
    String max;
    String errorCount;
    String errorPercentage;

    public AutomationPerformanceTestingSummarizedResults(String elapsedTime, String hitRate, String average, String min,
                    String max, String errorCount, String errorPercentage) {
        this.elapsedTime = elapsedTime;
        this.hitRate = hitRate;
        this.average = average;
        this.min = min;
        this.max = max;
        this.errorCount = errorCount;
        this.errorPercentage = errorPercentage;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getHitRate() {
        return hitRate;
    }

    public void setHitRate(String hitRate) {
        this.hitRate = hitRate;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorPercentage() {
        return errorPercentage;
    }

    public void setErrorPercentage(String errorPercentage) {
        this.errorPercentage = errorPercentage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AutomationPerformanceTestingSummarizedResults [");
        if (elapsedTime != null) {
            builder.append("elapsedTime=").append(elapsedTime).append(", ");
        }
        if (hitRate != null) {
            builder.append("hitRate=").append(hitRate).append(", ");
        }
        if (average != null) {
            builder.append("average=").append(average).append(", ");
        }
        if (min != null) {
            builder.append("min=").append(min).append(", ");
        }
        if (max != null) {
            builder.append("max=").append(max).append(", ");
        }
        if (errorCount != null) {
            builder.append("errorCount=").append(errorCount).append(", ");
        }
        if (errorPercentage != null) {
            builder.append("errorPercentage=").append(errorPercentage);
        }
        builder.append("]");
        return builder.toString();
    }

}
