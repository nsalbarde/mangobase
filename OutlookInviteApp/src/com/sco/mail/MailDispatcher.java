package com.sco.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailDispatcher {

	private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
	private Properties props = null;
	private Session session = null;
	private MimeMessage message = null;

	public static void main(String[] args) {
		System.out.println("Username : "+args[0]);
		MailDispatcher md = new MailDispatcher();

		try {
			md.prepeareMailDispatcher();
			md.sendInvite(args[0], args[1]);
			System.out.println("Invitation Sent!!!");
			
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		System.exit(0);
	}
	
	public void prepeareMailDispatcher() throws AddressException, MessagingException, Exception{		
			props = getProperties();
			session = Session.getDefaultInstance(props, null);
			message = createMessage();
	}

	private void sendInvite(String email, String password) throws NoSuchProviderException, MessagingException {
		Transport transport = session.getTransport("smtp");
		transport.connect(email, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	private Properties getProperties() throws IOException, ParseException {
		InputStream inStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		Properties props = new Properties();
		if (inStream != null) {
			props.load(inStream);
		} else {
			throw new FileNotFoundException("Not able to find application.properties file!!!");
		}
		return props;
	}

	private MimeMessage createMessage() throws MessagingException, AddressException, Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(props.getProperty("fromemail")));
		message.setSubject(props.getProperty("subject"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(props.getProperty("toemail")));
		message.setContent(getMultipart());

		return message;
	}

	private Multipart getMultipart() throws MessagingException, Exception {
		Multipart multipart = new MimeMultipart("alternative");

		BodyPart messageBodyPart = buildHtmlTextPart();
		multipart.addBodyPart(messageBodyPart);

		BodyPart calendarPart = buildCalendarPart();
		multipart.addBodyPart(calendarPart);
		return multipart;
	}

	private BodyPart buildHtmlTextPart() throws MessagingException {

		MimeBodyPart descriptionPart = new MimeBodyPart();
		String content = "<font size='2'>simple meeting invitation</font>";
		descriptionPart.setContent(content, "text/html; charset=utf-8");
		return descriptionPart;
	}

	private BodyPart buildCalendarPart() throws Exception {
		BodyPart calendarPart = new MimeBodyPart();
		Calendar cal = Calendar.getInstance();
		Date start = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(props.getProperty("startdate"));
		System.out.println("Start date : " + start.toString());
		cal.setTime(start);
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(props.getProperty("duration")));
		Date end = cal.getTime();
		System.out.println("End Date : " + end.toString());

		// check the icalendar spec in order to build a more complicated meeting
		// request
		String calendarContent = "BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n" + "PRODID: BCP - Meeting\n" + "VERSION:2.0\n"
				+ "BEGIN:VEVENT\n" + "DTSTAMP:"
				+ iCalendarDateFormat.format(start)
				+ "\n"
				+ "DTSTART:"
				+ iCalendarDateFormat.format(start)
				+ "\n"
				+ "DTEND:"
				+ iCalendarDateFormat.format(end)
				+ "\n"
				+ "SUMMARY:test request\n"
				+ "UID:324\n"
				+ "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:"+props.getProperty("fromemail")+"\n"
				+ "ORGANIZER:MAILTO:"+props.getProperty("fromemail")+"\n"
				+ "LOCATION:"+props.getProperty("location")+"\n"
				+ "DESCRIPTION:learn some stuff\n"
				+ "SEQUENCE:0\n"
				+ "PRIORITY:5\n"
				+ "CLASS:PUBLIC\n"
				+ "STATUS:CONFIRMED\n"
				+ "TRANSP:OPAQUE\n"
				+ "BEGIN:VALARM\n"
				+ "ACTION:DISPLAY\n"
				+ "DESCRIPTION:REMINDER\n"
				+ "TRIGGER;RELATED=START:-PT00H15M00S\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR";

		calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
		calendarPart.setContent(calendarContent, "text/calendar;method=CANCEL");
		return calendarPart;
	}
}
