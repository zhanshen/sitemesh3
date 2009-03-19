package com.opensymphony.sitemesh.html.rules;

import com.opensymphony.sitemesh.tagprocessor.Tag;
import com.opensymphony.sitemesh.tagprocessor.BasicBlockRule;

import java.io.IOException;

/**
 * Extracts the contents of any elements that look like
 * <code>&lt;content tag='foo'&gt;...&lt;/content&gt;</code> and write the contents
 * to a page property (page.foo).
 *
 * <p>This is a cheap and cheerful mechanism for embedding multiple components in a
 * page that can be used in different places in decorators.</p>
 *
 * @author Joe Walnes
 */
public class ContentBlockExtractingRule extends BasicBlockRule<String> {

    private final PageBuilder page;

    public ContentBlockExtractingRule(PageBuilder page) {
        super("content");
        this.page = page;
    }

    @Override
    protected String processStart(Tag tag) throws IOException {
        context.pushBuffer();
        return tag.getAttributeValue("tag", false);
    }

    @Override
    protected void processEnd(Tag tag, String tagId) throws IOException {
        page.addProperty("page." + tagId, context.currentBufferContents());
        context.popBuffer();
    }

}
