package com.wandrell.tabletop.punkapocalyptic.service.ruleset.command;

import static com.google.common.base.Preconditions.checkNotNull;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.tabletop.punkapocalyptic.model.inventory.Equipment;
import com.wandrell.tabletop.punkapocalyptic.model.inventory.Weapon;
import com.wandrell.tabletop.punkapocalyptic.model.unit.GroupedUnit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Unit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.mutation.MutantUnit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.mutation.Mutation;

public final class GetUnitValorationCommand implements ReturnCommand<Integer> {

    private final Unit unit;

    public GetUnitValorationCommand(final Unit unit) {
        super();

        checkNotNull(unit, "Received a null pointer as unit");

        this.unit = unit;
    }

    @Override
    public final Integer execute() {
        Integer cost;

        cost = getUnit().getBaseCost();

        for (final Weapon weapon : getUnit().getWeapons()) {
            cost += weapon.getCost();
        }

        for (final Equipment equipment : getUnit().getEquipment()) {
            cost += equipment.getCost();
        }

        if (getUnit().getArmor() != null) {
            cost += getUnit().getArmor().getCost();
        }

        if (getUnit() instanceof MutantUnit) {
            for (final Mutation mutation : ((MutantUnit) getUnit())
                    .getMutations()) {
                cost += mutation.getCost();
            }
        }

        if (getUnit() instanceof GroupedUnit) {
            cost = cost * ((GroupedUnit) getUnit()).getGroupSize().getValue();
        }

        return cost;
    }

    private final Unit getUnit() {
        return unit;
    }

}