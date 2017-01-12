/*
 * Created on 2004-09-16
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.challabros.birdletter.admin.util;

/**
 * @author eulks
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings("serial")
public class FormatCalendar extends GregorianCalendar {

	protected SimpleDateFormat simpleDateFormat = null;

	public FormatCalendar() {
		super();
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(int year, int month, int date) {
		super(year, month, date);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(int year, int month, int date, int hour, int minute) {
		super(year, month, date, hour, minute);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(int year, int month, int date, int hour, int minute, int second) {
		super(year, month, date, hour, minute, second);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(Locale aLocale) {
		super(aLocale);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(TimeZone zone) {
		super(zone);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
		simpleDateFormat = new SimpleDateFormat();
	}

	public FormatCalendar(String pattern) {
		super();
		simpleDateFormat = new SimpleDateFormat(pattern);
	}

	public FormatCalendar(SimpleDateFormat simpleDateFormat) {
		super();
		this.simpleDateFormat = simpleDateFormat;
	}

	public void applyPattern(String pattern) {
		simpleDateFormat.applyPattern(pattern);
	}

	public String format() {
		return simpleDateFormat.format(getTime());
	}

	public String format(String pattern) {
		String result = null;

		String oldPattern = toPattern();
		applyPattern(pattern);
		result = format();
		applyPattern(oldPattern);

		return result;
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void parse(String text) throws java.text.ParseException {
		setTime(simpleDateFormat.parse(text));
	}

	public void parse(String pattern, String text)
			throws java.text.ParseException {
		String oldPattern = toPattern();
		applyPattern(pattern);
		parse(text);
		applyPattern(oldPattern);
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}

	public String toPattern() {
		return simpleDateFormat.toPattern();
	}
}
