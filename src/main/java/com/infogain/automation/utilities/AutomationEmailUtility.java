package com.infogain.automation.utilities;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infogain.automation.properties.AutomationProperties;

@Component
public class AutomationEmailUtility {
    private static final Logger logger = LogManager.getLogger(AutomationEmailUtility.class);

    private Properties props;
    public static final String EMAIL_FROM = "emailFrom";
    public static final String PORT = "emailport";
    public static final String EMAIL_HOST = "emailHost";
    public static final String EMAIL_TO = "emailTo";
    public static final String EMAIL_SUBJECT = "emailSubject";
    public static final String MESSAGE_BODY = "messageBody";
    private String emailFrom;
    private int emailPort;
    private String emailHost;
    private String emailTo;
    private List<String> attachmentPaths;
    private String emailSubject;
    private String messageBody;
    private int totalExecutedTestCases;
    private int lastExecutedTestCount;
    private int totalPassTestCases;
    private int totalFailedTestCases;

    @Autowired
    public AutomationEmailUtility(final AutomationProperties automationProperties) {
        props = automationProperties.getProps();
        this.emailFrom = (String) props.get(EMAIL_FROM);
        this.emailPort = Integer.parseInt((String) props.get(PORT));
        this.emailHost = (String) props.get(EMAIL_HOST);
        this.emailTo = (String) props.get(EMAIL_TO);
        this.emailSubject = (String) props.get(EMAIL_SUBJECT);
        this.messageBody = (String) props.get(MESSAGE_BODY);
    }

    /**
     * @param attachmentPaths the attachmentPaths to set
     */
    public void addAttachments(List<String> attachmentPathstoadd) {
        if (attachmentPaths == null) {
            attachmentPaths = new ArrayList<>();
        }
        this.attachmentPaths.addAll(attachmentPathstoadd);
    }

    /**
     * This method adds the attachment
     * 
     * @param attachment - file which needs to be attached
     * @since Dec 11, 2019
     */
    public void addAttachment(String attachment) {
        if (attachmentPaths == null) {
            attachmentPaths = new ArrayList<>();
        }
        this.attachmentPaths.add(attachment);
    }

    public int getTotalExecutedTestCases() {
        return totalExecutedTestCases;
    }

    public void setTotalExecutedTestCases(int totalExecutedTestCases) {
        this.totalExecutedTestCases = totalExecutedTestCases;
    }

    public int getLastExecutedTestCount() {
        return lastExecutedTestCount;
    }

    public void setLastExecutedTestCount(int lastExecutedTestCount) {
        this.lastExecutedTestCount = lastExecutedTestCount;
    }

    public int getTotalPassTestCases() {
        return totalPassTestCases;
    }

    public void setTotalPassTestCases(int totalPassTestCases) {
        this.totalPassTestCases = totalPassTestCases;
    }

    public int getTotalFailedTestCases() {
        return totalFailedTestCases;
    }

    public void setTotalFailedTestCases(int totalFailedTestCases) {
        this.totalFailedTestCases = totalFailedTestCases;
    }

    /**
     * This method sets the session and its properties to send a mail
     * 
     * @since Dec 11, 2019
     */
    public void sendMail() {
        logger.traceEntry("sendMail method of AutomationEmailUtility class");
        // Making Properties object which will further pass as argument to Session
        // object
        double passPercentage;
        double failPercentage;
        int newTestCases = 0;
        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.host", this.emailHost);
        emailProps.put("mail.smtp.port", Integer.valueOf(this.emailPort));

        // Setting up a mail session
        Session session = Session.getInstance(emailProps);

        try {
            InternetAddress[] receiversAddress = InternetAddress.parse(this.emailTo, true);

            InetAddress addr = InetAddress.getLocalHost();
            String machineName = addr.getHostName();

            // Setting up message properties
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.emailFrom));
            message.setRecipients(Message.RecipientType.TO, receiversAddress);
            Multipart multipart = new MimeMultipart();
            for (String fName : attachmentPaths) {
                File file = new File(fName);
                if (file.exists()) {
                    addAttachmentInMultipart(multipart, fName);
                }
            }
            BodyPart htmlBodyPart = new MimeBodyPart();
            String msgBody = this.messageBody.replace("totalExecutedCount", String.valueOf(totalExecutedTestCases));
            msgBody = msgBody.replace("TotalPassedCount", String.valueOf(totalPassTestCases));
            msgBody = msgBody.replace("TotalFailedCount", String.valueOf(totalFailedTestCases));
            passPercentage = ((double) totalPassTestCases / (double) totalExecutedTestCases) * 100;
            failPercentage = ((double) totalFailedTestCases * 100) / (double) totalExecutedTestCases;
            msgBody = msgBody.replace("passPercentage", String.format("%.2f", passPercentage) + "%");
            msgBody = msgBody.replace("failPercentage", String.format("%.2f", failPercentage) + "%");
            newTestCases = totalExecutedTestCases - lastExecutedTestCount;
            msgBody = msgBody.replace("testCasesAdded", String.valueOf(Math.abs(newTestCases)));
            if (newTestCases < 0) {
                msgBody = msgBody.replace("Test Cases Added", "Test Cases Deleted");
            }
            htmlBodyPart.setContent(msgBody, "text/html");
            multipart.addBodyPart(htmlBodyPart);
            message.setContent(multipart);
            message.setSubject(this.emailSubject + machineName);
            logger.debug("Sending email notification");
            Transport.send(message);
            attachmentPaths = null;
            logger.info("Mail sent successfully");
        } catch (MessagingException | UnknownHostException e) {
            logger.debug("Exception Occured While sending mail {} ", ExceptionUtils.getStackTrace(e));
        }
        finally {
            resetMailData();
        }
        logger.traceExit();
    }

    private void resetMailData() {
        totalExecutedTestCases = 0;
        lastExecutedTestCount = 0;
        totalPassTestCases = 0;
        totalFailedTestCases = 0;
        
    }

    /**
     * This method is used to attach an Attachment to the mail
     * 
     * @param multipart of {@link Multipart} that contains multiple Body Parts
     * @param fileName - Name of Attached File
     * @throws MessagingException message
     * @since Dec 11, 2019
     */
    private void addAttachmentInMultipart(Multipart multipart, String fileName) throws MessagingException {
        logger.traceEntry("addAttachment method of AutomationEmailUtility class");
        DataSource source = new FileDataSource(fileName);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName.substring(fileName.lastIndexOf('/')));
        logger.info("Adding attachment with path {} in email", fileName);
        multipart.addBodyPart(messageBodyPart);
        logger.trace("Exit addAttachment()");
    }
}
