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
import com.wandrell.tabletop.business.model.punkapocalyptic.RangedValue;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.MeleeWeapon;
import com.wandrell.tabletop.business.model.punkapocalyptic.inventory.RangedWeapon;
import com.wandrell.tabletop.business.service.punkapocalyptic.ModelService;
import com.wandrell.tabletop.business.util.tag.punkapocalyptic.service.ModelServiceAware;
import com.wandrell.util.command.ReturnCommand;

public final class ParseRangedWeaponsCommand implements
        ReturnCommand<Map<String, RangedWeapon>>, ModelServiceAware {

    private final Document    document;
    private final MeleeWeapon melee;
    private ModelService      modelService;

    public ParseRangedWeaponsCommand(final Document doc, final MeleeWeapon melee) {
        super();

        checkNotNull(doc, "Received a null pointer as document");
        checkNotNull(melee, "Received a null pointer as melee weapon");

        document = doc;
        this.melee = melee;
    }

    @Override
    public final Map<String, RangedWeapon> execute() throws Exception {
        final Map<String, RangedWeapon> weapons;
        final Collection<Element> nodes;
        RangedWeapon weapon;

        nodes = XPathFactory.instance()
                .compile("//weapon_ranged_profile", Filters.element())
                .evaluate(getDocument());

        weapons = new LinkedHashMap<>();
        for (final Element node : nodes) {
            weapon = parseNode(node);
            weapons.put(weapon.getName(), weapon);
        }

        return weapons;
    }

    @Override
    public final void setModelService(final ModelService service) {
        modelService = service;
    }

    private final MeleeWeapon getDefaultMeleeWeapon() {
        return melee;
    }

    private final Document getDocument() {
        return document;
    }

    private final ModelService getModelService() {
        return modelService;
    }

    private final RangedWeapon parseNode(final Element node) {
        final String name;
        final Element strength;
        final Element penetration;
        final Element range;
        final Element distanceCMNode;
        final Element distanceInchNode;
        final Integer strengthShort;
        final Integer strengthMedium;
        final Integer strengthLong;
        final Integer penetrationShort;
        final Integer penetrationMedium;
        final Integer penetrationLong;
        final Integer distanceShortCM;
        final Integer distanceMediumCM;
        final Integer distanceLongCM;
        final Integer distanceShortInches;
        final Integer distanceMediumInches;
        final Integer distanceLongInches;
        final Integer cost;
        final RangedValue distanceCM;
        final RangedValue distanceInches;
        final RangedValue strengthRanged;
        final RangedValue penetrationRanged;
        final RangedWeapon weapon;

        name = node.getChildText(ModelNodeConf.NAME);

        strength = node.getChild(ModelNodeConf.STRENGTH);
        strengthShort = Integer.parseInt(strength
                .getChildText(ModelNodeConf.SHORT));
        strengthMedium = Integer.parseInt(strength
                .getChildText(ModelNodeConf.MEDIUM));
        strengthLong = Integer.parseInt(strength
                .getChildText(ModelNodeConf.LONG));

        penetration = node.getChild(ModelNodeConf.PENETRATION);
        penetrationShort = Integer.parseInt(penetration
                .getChildText(ModelNodeConf.SHORT));
        penetrationMedium = Integer.parseInt(penetration
                .getChildText(ModelNodeConf.MEDIUM));
        penetrationLong = Integer.parseInt(penetration
                .getChildText(ModelNodeConf.LONG));

        range = node.getChild(ModelNodeConf.RANGE);

        distanceInchNode = range.getChild(ModelNodeConf.INCHES);
        distanceShortInches = Integer.parseInt(distanceInchNode
                .getChildText(ModelNodeConf.SHORT));
        distanceMediumInches = Integer.parseInt(distanceInchNode
                .getChildText(ModelNodeConf.MEDIUM));
        distanceLongInches = Integer.parseInt(distanceInchNode
                .getChildText(ModelNodeConf.LONG));

        distanceCMNode = range.getChild(ModelNodeConf.CENTIMETERS);
        distanceShortCM = Integer.parseInt(distanceCMNode
                .getChildText(ModelNodeConf.SHORT));
        distanceMediumCM = Integer.parseInt(distanceCMNode
                .getChildText(ModelNodeConf.MEDIUM));
        distanceLongCM = Integer.parseInt(distanceCMNode
                .getChildText(ModelNodeConf.LONG));

        cost = Integer.parseInt(node.getChildText(ModelNodeConf.COST));

        distanceCM = getModelService().getRangedValue(distanceShortCM,
                distanceMediumCM, distanceLongCM);
        distanceInches = getModelService().getRangedValue(distanceShortInches,
                distanceMediumInches, distanceLongInches);

        penetrationRanged = getModelService().getRangedValue(penetrationShort,
                penetrationMedium, penetrationLong);
        strengthRanged = getModelService().getRangedValue(strengthShort,
                strengthMedium, strengthLong);

        weapon = getModelService().getRangedWeapon(name, cost,
                penetrationRanged, strengthRanged, distanceCM, distanceInches,
                getDefaultMeleeWeapon());

        return weapon;
    }

}
