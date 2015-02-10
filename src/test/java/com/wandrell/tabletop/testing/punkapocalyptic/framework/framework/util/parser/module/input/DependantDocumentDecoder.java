package com.wandrell.tabletop.testing.punkapocalyptic.framework.framework.util.parser.module.input;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wandrell.pattern.parser.xml.input.JDOMDocumentDecoder;
import com.wandrell.tabletop.testing.punkapocalyptic.framework.framework.conf.TestXMLConf;

public final class DependantDocumentDecoder implements
        JDOMDocumentDecoder<Collection<Collection<Object>>> {

    private static final Logger logger = LoggerFactory
                                               .getLogger(DependantDocumentDecoder.class);

    private static final Logger getLogger() {
        return logger;
    }

    public DependantDocumentDecoder() {
        super();
    }

    @Override
    public final Collection<Collection<Object>> decode(final Document doc) {
        final Collection<Collection<Object>> colData;

        colData = new LinkedHashSet<>();
        for (final Element node : doc.getRootElement().getChildren()) {
            colData.add(readNode(node));
        }

        return colData;
    }

    private final Collection<Object> readNode(final Element node) {
        final Collection<Object> data;
        final Integer dependant;
        final Integer master;
        final Integer range;

        dependant = Integer.parseInt(node.getChild(TestXMLConf.NODE_DEPENDANT)
                .getText());
        master = Integer.parseInt(node.getChild(TestXMLConf.NODE_MASTER)
                .getText());
        range = Integer.parseInt(node.getChild(TestXMLConf.NODE_RANGE)
                .getText());

        data = new LinkedList<>();
        data.add(dependant);
        data.add(master);
        data.add(range);

        getLogger()
                .debug(String
                        .format("Read constraint for %d dependant units, where %d are master units, and with range %d",
                                dependant, master, range));

        return data;
    }

}
