package com.intel.sto.bigdata.app.asp.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
  public static void send(String host, String proxyHost, String port, String user, String password,
      String from, String to, String subject, String content) {
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.auth", "true");
    props.put("proxySet", "true");
    props.put("socksProxyHost", proxyHost);
    props.put("socksProxyPort", port);
    try {
      Session mailSession = Session.getDefaultInstance(props);
      Message message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(content);
      message.saveChanges();
      Transport transport = mailSession.getTransport("smtp");
      transport.connect(host, user, password);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
