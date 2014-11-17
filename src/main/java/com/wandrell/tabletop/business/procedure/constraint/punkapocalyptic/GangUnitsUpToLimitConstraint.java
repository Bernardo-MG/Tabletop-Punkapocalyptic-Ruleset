package com.wandrell.tabletop.business.procedure.constraint.punkapocalyptic;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.wandrell.tabletop.business.model.procedure.constraint.punkapocalyptic.GangConstraint;
import com.wandrell.tabletop.business.model.punkapocalyptic.unit.Gang;
import com.wandrell.tabletop.business.model.valuehandler.ValueHandler;

public final class GangUnitsUpToLimitConstraint implements GangConstraint {

    private final String       message;
    private final ValueHandler unitsLimit;

    public GangUnitsUpToLimitConstraint(final ValueHandler unitsLimit,
            final String message) {
        super();

        checkNotNull(unitsLimit, "Received a null pointer as units limit");
        checkNotNull(message, "Received a null pointer as message");

        this.unitsLimit = unitsLimit;
        this.message = message;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GangUnitsUpToLimitConstraint other = (GangUnitsUpToLimitConstraint) obj;
        return Objects.equals(unitsLimit, other.unitsLimit);
    }

    @Override
    public final String getErrorMessage() {
        return message;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass().getName(), unitsLimit);
    }

    @Override
    public final Boolean isValid(final Gang gang) {
        checkArgument(getUnitsLimit() >= 0,
                "The limit should be positive or zero");

        return (gang.getUnits().size() <= getUnitsLimit());
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("limit", unitsLimit)
                .toString();
    }

    private final Integer getUnitsLimit() {
        return unitsLimit.getValue();
    }

}
