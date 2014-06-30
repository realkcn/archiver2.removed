package org.kbs.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class JSGoTag extends PagerSupport {
	private String elementid = "pager.gopage";

	public final String getElementid() {
		return elementid;
	}

	public final void setElementid(String elementid) {
		this.elementid = elementid;
	}

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		try {
			pageContext.getOut().append(
					"<a href=\"javascript:void(0);\" onclick=\"javascript:_kbspagergo('"
							+ elementid + "')\">");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().append("</a>");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}
}
