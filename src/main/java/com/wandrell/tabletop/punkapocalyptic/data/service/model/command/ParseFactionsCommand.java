package com.wandrell.tabletop.punkapocalyptic.data.service.model.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.tabletop.punkapocalyptic.model.faction.Faction;
import com.wandrell.tabletop.punkapocalyptic.service.ModelService;
import com.wandrell.tabletop.punkapocalyptic.util.tag.service.ModelServiceAware;

public final class ParseFactionsCommand implements
        ReturnCommand<Collection<Faction>>, ModelServiceAware {

    private final Document document;
    private ModelService   modelService;

    public ParseFactionsCommand(final Document doc) {
        super();

        checkNotNull(doc, "Received a null pointer as document");

        document = doc;
    }

    @Override
    public final Collection<Faction> execute() throws Exception {
        final Collection<Faction> factions;
        final Collection<Element> nodes;
        String name;

        nodes = XPathFactory.instance()
                .compile("//faction_profile", Filters.element())
                .evaluate(getDocument());

        factions = new LinkedList<>();
        for (final Element node : nodes) {
            name = node.getChildText("name");
            factions.add(getModelService().getFaction(name));
        }

        return factions;
    }

    @Override
    public final void setModelService(final ModelService service) {
        modelService = service;
    }

    private final Document getDocument() {
        return document;
    }

    private final ModelService getModelService() {
        return modelService;
    }

}