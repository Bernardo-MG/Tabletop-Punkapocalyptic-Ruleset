package com.wandrell.tabletop.testing.punkapocalyptic.framework.framework.util.parser;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wandrell.pattern.parser.Parser;
import com.wandrell.tabletop.testing.punkapocalyptic.framework.framework.conf.TestXMLConf;

public final class DependantDocumentParser implements
        Parser<Document, Collection<Collection<Object>>> {

    private static final Logger logger = LoggerFactory
                                               .getLogger(DependantDocumentParser.class);

    private static final Logger getLogger() {
        return logger;
    }

    public DependantDocumentParser() {
        super();
    }

    @Override
    public final Collection<Collection<Object>> parse(final Document doc) {
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
