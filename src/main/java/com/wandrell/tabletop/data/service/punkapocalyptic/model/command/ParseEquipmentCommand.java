package com.wandrell.tabletop.data.service.punkapocalyptic.model.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;

import com.wandrell.tabletop.business.conf.punkapocalyptic.ModelNodeConf;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.DefaultEquipment;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.Equipment;
import com.wandrell.util.command.ReturnCommand;

public final class ParseEquipmentCommand implements
        ReturnCommand<Map<String, Equipment>> {

    private final Document document;

    public ParseEquipmentCommand(final Document doc) {
        super();

        checkNotNull(doc, "Received a null pointer as document");

        document = doc;
    }

    @Override
    public final Map<String, Equipment> execute() throws Exception {
        final Map<String, Equipment> equipment;
        final Collection<Element> nodes;
        Equipment equip;

        nodes = XPathFactory.instance()
                .compile("//equipment_profile", Filters.element())
                .evaluate(getDocument());

        equipment = new LinkedHashMap<>();
        for (final Element node : nodes) {
            equip = parseNode(node);
            equipment.put(equip.getName(), equip);
        }

        return equipment;
    }

    private final Document getDocument() {
        return document;
    }

    private final Equipment parseNode(final Element node) {
        final Equipment equip;
        final String name;
        final Integer cost;

        name = node.getChildText(ModelNodeConf.NAME);
        cost = Integer.parseInt(node.getChildText(ModelNodeConf.COST));

        equip = new DefaultEquipment(name, cost);

        return equip;
    }

}