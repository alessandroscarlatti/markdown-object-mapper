package com.scarlatti

import org.apache.commons.beanutils.PropertyUtils
import org.apache.commons.beanutils.PropertyUtilsBean
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.Heading
import org.commonmark.node.Node
import org.commonmark.node.Text
import org.commonmark.parser.Parser

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 10/16/2018
 */
class MapperInternal<T> {

    private String source
    private T instance

    MapperInternal(String source, T instance) {
        this.source = source
        this.instance = instance
    }

    /**
     * Apply the values from the source to the instance.
     */
    void map() {
        Parser parser = Parser.builder().build()
        Node document = parser.parse(source)

        // now find all the second level headings and find all fenced code blocks
        document.accept(new AbstractVisitor() {
            @Override
            void visit(Heading heading) {
                if (heading.level == 2) {
                    if (heading.getNext() instanceof FencedCodeBlock) {
                        String headingName = ((Text)heading.getFirstChild()).literal
                        applyFencedCodeBlock(headingName, (FencedCodeBlock) heading.getNext())
                    }
                }
                else {
                    super.visit(heading)
                }
            }
        })
    }

    private void applyFencedCodeBlock(String name, FencedCodeBlock fencedCodeBlock) {
        if (PropertyUtils.isWriteable(instance, name)) {
            PropertyUtils.setSimpleProperty(instance, name, fencedCodeBlock.literal)
        }
    }
}
