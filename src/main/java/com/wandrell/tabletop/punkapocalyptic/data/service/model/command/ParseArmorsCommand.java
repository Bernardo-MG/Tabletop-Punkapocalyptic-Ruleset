package com.wandrell.tabletop.punkapocalyptic.data.service.model.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.pattern.repository.Repository;
import com.wandrell.tabletop.punkapocalyptic.conf.ModelNodeConf;
import com.wandrell.tabletop.punkapocalyptic.model.inventory.Armor;
import com.wandrell.tabletop.punkapocalyptic.model.ruleset.SpecialRule;
import com.wandrell.tabletop.punkapocalyptic.service.ModelService;
import com.wandrell.tabletop.punkapocalyptic.util.tag.service.ModelServiceAware;

public final class ParseArmorsCommand implements
        ReturnCommand<Collection<Armor>>, ModelServiceAware {

    private final Document                document;
    private ModelService                  modelService;
    private final Repository<SpecialRule> ruleRepository;

    public ParseArmorsCommand(final Document doc,
            final Repository<SpecialRule> ruleRepository) {
        super();
        // TODO: Transform into parsers

        checkNotNull(doc, "Received a null pointer as document");
        checkNotNull(ruleRepository,
                "Received a null pointer as rules repository");

        document = doc;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public final Collection<Armor> execute() throws Exception {
        final Collection<Armor> armors;
        final Collection<Element> nodes;

        nodes = XPathFactory.instance()
                .compile("//armor_profile", Filters.element())
                .evaluate(getDocument());

        armors = new LinkedList<>();
        for (final Element node : nodes) {
            armors.add(parseNode(node));
        }

        return armors;
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

    private final Repository<SpecialRule> getRuleRepository() {
        return ruleRepository;
    }

    private final Collection<SpecialRule> getRules(final Element rulesNode) {
        Collection<SpecialRule> rules;

        rules = new LinkedList<>();
        if (rulesNode != null) {
            for (final Element rule : rulesNode.getChildren()) {
                rules.add(getRuleRepository()
                        .getCollection(r -> r.getName().equals(rule.getText()))
                        .iterator().next());
            }
        }

        return rules;
    }

    private final Armor parseNode(final Element node) {
        final String name;
        final Integer protection;
        final Collection<SpecialRule> rules;
        Armor armor;

        name = node.getChildText(ModelNodeConf.NAME);
        protection = Integer.parseInt(node
                .getChildText(ModelNodeConf.PROTECTION));
        rules = getRules(node.getChild(ModelNodeConf.RULES));

        armor = getModelService().getArmor(name, protection, rules);

        return armor;
    }

}