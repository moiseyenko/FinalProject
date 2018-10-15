package by.epam.hotel.customtags;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import by.epam.hotel.entity.SessionData;
import by.epam.hotel.util.constant.AttributeConstant;

public class LocalDateFormatTag extends BodyTagSupport {
	private static final long serialVersionUID = -43577037147502463L;
	private final Locale ruLocale = new Locale("ru", "RU");
	private LocalDate date;
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public int doStartTag() throws JspException {
		SessionData sessionData = (SessionData)pageContext.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		Locale currentLocale = sessionData.getLocale();
		String formattedString;
			if(ruLocale.equals(currentLocale)) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU"));
				formattedString = date.format(formatter);
			}else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", new Locale("en", "US"));
				formattedString = date.format(formatter);
			}
		try {
			JspWriter out = pageContext.getOut();
			out.write(formattedString);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
