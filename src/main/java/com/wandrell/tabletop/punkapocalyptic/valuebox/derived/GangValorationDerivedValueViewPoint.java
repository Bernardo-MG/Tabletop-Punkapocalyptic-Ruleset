package com.wandrell.tabletop.punkapocalyptic.valuebox.derived;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.EventObject;

import com.wandrell.tabletop.event.ValueChangeEvent;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Gang;
import com.wandrell.tabletop.punkapocalyptic.model.unit.event.GangListener;
import com.wandrell.tabletop.punkapocalyptic.model.unit.event.GangListenerAdapter;
import com.wandrell.tabletop.punkapocalyptic.service.RulesetService;
import com.wandrell.tabletop.punkapocalyptic.util.tag.GangAware;
import com.wandrell.tabletop.valuebox.derived.AbstractDerivedValueViewPoint;
import com.wandrell.tabletop.valuebox.derived.DerivedValueViewPoint;

public final class GangValorationDerivedValueViewPoint extends
        AbstractDerivedValueViewPoint implements GangAware {

    private Gang                 gang;
    private final GangListener   listener;
    private final RulesetService serviceRuleset;

    {
        final DerivedValueViewPoint source = this;
        listener = new GangListenerAdapter() {

            @Override
            public final void valorationChanged(final EventObject event) {
                fireValueChangedEvent(new ValueChangeEvent(source,
                        source.getValue(), source.getValue()));
            }

        };
    }

    public GangValorationDerivedValueViewPoint(final Gang gang,
            final RulesetService service) {
        super();

        checkNotNull(gang, "Received a null pointer as gang");
        checkNotNull(service, "Received a null pointer as service");

        this.gang = gang;
        serviceRuleset = service;

        gang.addGangListener(getListener());
    }

    public GangValorationDerivedValueViewPoint(
            final GangValorationDerivedValueViewPoint store) {
        super();

        checkNotNull(store, "Received a null pointer as store");

        gang = store.gang;
        serviceRuleset = store.serviceRuleset;
    }

    @Override
    public final Integer getValue() {
        return getRulesetService().getGangValoration(getGang());
    }

    @Override
    public final void setGang(final Gang gang) {
        checkNotNull(gang, "Received a null pointer as gang");

        this.gang = gang;

        gang.addGangListener(getListener());
    }

    private final Gang getGang() {
        return gang;
    }

    private final GangListener getListener() {
        return listener;
    }

    private final RulesetService getRulesetService() {
        return serviceRuleset;
    }

}