package org.wikicrimes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Util {

	/*
	 * public static ResourceBundle getMessages() { ApplicationFactory factory =
	 * (ApplicationFactory) FactoryFinder
	 * .getFactory(FactoryFinder.APPLICATION_FACTORY); String bundleName =
	 * factory.getApplication().getMessageBundle(); ResourceBundle rb =
	 * ResourceBundle.getBundle(bundleName,
	 * FacesContext.getCurrentInstance().getViewRoot().getLocale()); return rb;
	 * }
	 */

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String formatDate(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(data);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date toDate(String data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}

	private static Browser getClientBrowser(HttpServletRequest req) {
		String s = req.getHeader("user-agent");
		if (s == null)
			return Browser.UNKNOWN;
		if (s.indexOf("MSIE") > -1)
			return Browser.INTERNET_EXPLORER;
		else if (s.indexOf("Netscape") > -1)
			return Browser.NETSCAPE;
		else
			return Browser.UNKNOWN;
		// TODO fazer os outros quando precisar...
	}
	
	public enum Browser{ INTERNET_EXPLORER, NETSCAPE, UNKNOWN }

	public static boolean isClientUsingIE(HttpServletRequest req) {
		return getClientBrowser(req) == Browser.INTERNET_EXPLORER;
	}

}
