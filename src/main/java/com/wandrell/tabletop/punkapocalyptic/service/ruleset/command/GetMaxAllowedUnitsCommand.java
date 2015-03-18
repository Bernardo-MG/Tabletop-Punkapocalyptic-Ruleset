package com.wandrell.tabletop.punkapocalyptic.service.ruleset.command;

import static com.google.common.base.Preconditions.checkNotNull;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Gang;

public final class GetMaxAllowedUnitsCommand implements ReturnCommand<Integer> {

    private static final Integer RANGE = 100;
    private static final Integer STEP  = 3;
    private final Gang           gang;

    public GetMaxAllowedUnitsCommand(final Gang gang) {
        super();

        checkNotNull(gang, "Received a null pointer as gang");

        this.gang = gang;
    }

    @Override
    public final Integer execute() {
        Integer value;
        Integer max;

        value = getGang().getValoration();
        if (value == 0) {
            max = STEP;
        } else {
            max = 0;
            while (value > 0) {
                if (value > RANGE) {
                    value -= RANGE;
                } else {
                    value = 0;
                }
                max += STEP;
            }
        }

        return max;
    }

    private final Gang getGang() {
        return gang;
    }

}