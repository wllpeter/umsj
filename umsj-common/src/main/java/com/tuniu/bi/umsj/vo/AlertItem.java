package com.tuniu.bi.umsj.vo;

/**
 * @author muchen
 */
public class AlertItem {

    private String status;

    private CommonLabel labels;

    private CommonAnnotation annotations;

    private String startsAt;

    private String endsAt;

    private String generatorURL;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommonLabel getLabels() {
        return labels;
    }

    public void setLabels(CommonLabel labels) {
        this.labels = labels;
    }

    public CommonAnnotation getAnnotations() {
        return annotations;
    }

    public void setAnnotations(CommonAnnotation annotations) {
        this.annotations = annotations;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public String getGeneratorURL() {
        return generatorURL;
    }

    public void setGeneratorURL(String generatorURL) {
        this.generatorURL = generatorURL;
    }
}
