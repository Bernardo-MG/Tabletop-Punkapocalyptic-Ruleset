package com.wandrell.tabletop.data.service.punkapocalyptic.model.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.tabletop.business.conf.punkapocalyptic.ModelNodeConf;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.Equipment;
import com.wandrell.tabletop.business.service.punkapocalyptic.ModelService;
import com.wandrell.tabletop.business.util.tag.punkapocalyptic.service.ModelServiceAware;

public final class ParseEquipmentCommand implements
        ReturnCommand<Collection<Equipment>>, ModelServiceAware {

    private final Document document;
    private ModelService   modelService;

    public ParseEquipmentCommand(final Document doc) {
        super();

        checkNotNull(doc, "Received a null pointer as document");

        document = doc;
    }

    @Override
    public final Collection<Equipment> execute() throws Exception {
        final Collection<Equipment> equipment;
        final Collection<Element> nodes;

        nodes = XPathFactory.instance()
                .compile("//equipment_profile", Filters.element())
                .evaluate(getDocument());

        equipment = new LinkedList<>();
        for (final Element node : nodes) {
            equipment.add(parseNode(node));
        }

        return equipment;
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

    private final Equipment parseNode(final Element node) {
        final Equipment equip;
        final String name;
        final Integer cost;

        name = node.getChildText(ModelNodeConf.NAME);
        cost = Integer.parseInt(node.getChildText(ModelNodeConf.COST));

        equip = getModelService().getEquipment(name, cost);

        return equip;
    }

}
