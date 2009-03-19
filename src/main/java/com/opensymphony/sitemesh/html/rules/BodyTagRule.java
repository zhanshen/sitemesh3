package com.opensymphony.sitemesh.html.rules;

import com.opensymphony.sitemesh.tagprocessor.BasicBlockRule;
import com.opensymphony.sitemesh.tagprocessor.Tag;

import java.io.IOException;

/**
 * Extracts the contents of the &lt;body&gt; tag, writing into the passed in buffer.
 *
 * <p>Additionally, any attributes on the &lt;body&gt; tag (e.g. onclick) will be exported
 * to the page as properties under the 'body.' prefix (e.g. body.onclick).</p>
 *
 * <p>This rule also deals with documents that do not contain any &lt;body&gt; tags,
 * treating the entire document as the body instead.</p>
 *
 * @author Joe Walnes
 */
public class BodyTagRule extends BasicBlockRule {

    private final PageBuilder page;

    public BodyTagRule(PageBuilder page) {
        super("body");
        this.page = page;
    }

    @Override
    protected Object processStart(Tag tag) throws IOException {
        for (int i = 0; i < tag.getAttributeCount(); i++) {
            page.addProperty("body." + tag.getAttributeName(i), tag.getAttributeValue(i));
        }
        context.pushBuffer();
        return null;
    }

    @Override
    protected void processEnd(Tag tag, Object data) throws IOException {
        page.addProperty("body", context.currentBufferContents());
        context.popBuffer();
    }

}
