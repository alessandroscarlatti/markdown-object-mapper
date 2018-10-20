package com.scarlatti

import org.apache.commons.beanutils.ConvertUtils
import org.apache.commons.beanutils.ConvertUtilsBean2
import org.apache.commons.beanutils.PropertyUtils
import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Code
import org.commonmark.node.FencedCodeBlock
import org.commonmark.node.Heading
import org.commonmark.node.IndentedCodeBlock
import org.commonmark.node.Node
import org.commonmark.node.Paragraph
import org.commonmark.node.Text
import org.commonmark.parser.Parser

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 10/16/2018
 */
class MarkdownMapper {

    def <T> T readResourceAs(String resourcePath, Class<T> valueType) {

        InputStream stream = this.getClass().getResourceAsStream(resourcePath)
        if (stream == null) {
            throw new IllegalArgumentException("Unable to find resource: " + resourcePath)
        }
        String source = new Scanner(stream).useDelimiter("\\Z").next()
        return readValueAs(source, valueType)
    }

    def <T> T readValueAs(String source, Class<T> valueType) {
        T instance = valueType.newInstance()
        new MapperInternal<>(source, instance).map()
        return instance
    }

    void mapValueTo(String source, Object instance) {
        new MapperInternal<>(source, instance).map()
    }


    private static class MapperInternal<T> {

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

                        String headingName = ((Text) heading.getFirstChild()).literal

                        // ```fenced code block``` just after ## a heading
                        if (heading.getNext() instanceof FencedCodeBlock) {
                            applyFencedCodeBlock(headingName, (FencedCodeBlock) heading.getNext())
                        }

                        // `code block` just after ## a heading
                        if (heading.getNext() instanceof Paragraph && heading.getNext().getFirstChild() instanceof Code) {
                            println(((Code) heading.getNext().getFirstChild()).getLiteral())
                            applyCodeBlock(headingName, (Code) heading.getNext().getFirstChild())
                        }
                    } else {
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

        private void applyCodeBlock(String propertyName, Code codeBlock) {
            if (PropertyUtils.isWriteable(instance, propertyName)) {
                Class<?> propertyClass = PropertyUtils.getSimpleProperty(instance, propertyName).getClass()
                Object val = ConvertUtils.convert(codeBlock.literal, propertyClass)
                PropertyUtils.setSimpleProperty(instance, propertyName, val)
            }
        }
    }
}
