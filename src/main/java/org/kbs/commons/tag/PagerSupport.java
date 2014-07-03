package org.kbs.commons.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class PagerSupport extends TagSupport {
    private boolean isvalid = true;

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doJumpStartTag(int newpage) throws JspException {
        isvalid = getPager().isValidPage(newpage);
        // System.out.println(isvalid+" dostarttag");
        if (isvalid) {
            try {
                pageContext.getOut().append("<a href=\"" + getPager().generateURL(newpage) + "\">");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new JspException(e);
            }
            return EVAL_BODY_INCLUDE;
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        if (isvalid)
            try {
                pageContext.getOut().append("</a>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        return EVAL_PAGE;
    }

    /**
     * @return the pager
     */
    public Pager getPager() {
        return (Pager) pageContext.getRequest().getAttribute(PagerTag.getDefaultid());
    }
}
