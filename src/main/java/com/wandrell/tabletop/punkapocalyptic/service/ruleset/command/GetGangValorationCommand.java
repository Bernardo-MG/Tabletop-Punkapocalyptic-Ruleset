package com.wandrell.tabletop.punkapocalyptic.service.ruleset.command;

import static com.google.common.base.Preconditions.checkNotNull;

import com.wandrell.pattern.command.ResultCommand;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Gang;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Unit;
import com.wandrell.tabletop.punkapocalyptic.service.RulesetService;
import com.wandrell.tabletop.punkapocalyptic.util.tag.service.RulesetServiceAware;

public final class GetGangValorationCommand implements ResultCommand<Integer>,
        RulesetServiceAware {

    private Integer        cost;
    private final Gang     gang;
    private RulesetService serviceRuleset;

    public GetGangValorationCommand(final Gang gang) {
        super();

        checkNotNull(gang, "Received a null pointer as gang");

        this.gang = gang;
    }

    @Override
    public final void execute() {
        cost = 0;
        for (final Unit unit : getGang().getUnits()) {
            cost += unit.getValoration();
        }

        cost += (getGang().getBullets() * getRulesetService().getBulletCost());
    }

    @Override
    public final Integer getResult() {
        return cost;
    }

    public final RulesetService getRulesetService() {
        return serviceRuleset;
    }

    @Override
    public final void setRulesetService(final RulesetService service) {
        checkNotNull(service, "Received a null pointer as ruleset service");

        serviceRuleset = service;
    }

    private final Gang getGang() {
        return gang;
    }

}
