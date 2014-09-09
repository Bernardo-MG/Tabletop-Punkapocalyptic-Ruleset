package com.wandrell.tabletop.util.command.punkapocalyptic;

import com.wandrell.business.service.application.ApplicationInfoService;
import com.wandrell.business.service.swing.layout.LayoutService;
import com.wandrell.tabletop.business.service.punkapocalyptic.PunkapocalypticLocalizationService;
import com.wandrell.tabletop.business.service.punkapocalyptic.PunkapocalypticRulesetService;
import com.wandrell.tabletop.data.dao.punkapocalyptic.ArmorDAO;
import com.wandrell.tabletop.data.dao.punkapocalyptic.FactionDAO;
import com.wandrell.tabletop.data.dao.punkapocalyptic.RulesetDAO;
import com.wandrell.tabletop.data.dao.punkapocalyptic.UnitDAO;
import com.wandrell.tabletop.data.dao.punkapocalyptic.WeaponDAO;
import com.wandrell.tabletop.util.tag.punkapocalyptic.dao.ArmorDAOAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.dao.FactionDAOAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.dao.RulesetDAOAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.dao.UnitDAOAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.dao.WeaponDAOAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.service.ApplicationInfoServiceAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.service.LayoutServiceAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.service.LocalizationServiceAware;
import com.wandrell.tabletop.util.tag.punkapocalyptic.service.RulesetServiceAware;
import com.wandrell.util.command.Command;
import com.wandrell.util.command.CommandExecutor;
import com.wandrell.util.command.ReturnCommand;

public class PunkapocalypticContextCommandExecutor implements CommandExecutor {

    private ArmorDAO                           daoArmor;
    private FactionDAO                         daoFaction;
    private RulesetDAO                         daoSpecialRule;
    private UnitDAO                            daoUnit;
    private WeaponDAO                          daoWeapon;
    private final CommandExecutor              executor;
    private ApplicationInfoService             serviceApplicationInfo;
    private LayoutService                      serviceLayout;
    private PunkapocalypticLocalizationService serviceLocalization;
    private PunkapocalypticRulesetService      serviceRuleSet;

    public PunkapocalypticContextCommandExecutor(final CommandExecutor executor) {
        super();
        this.executor = executor;
    }

    @Override
    public final void execute(final Command command) {
        setContext(command);

        getExecutor().execute(command);
    }

    @Override
    public final <V> V execute(final ReturnCommand<V> command) {
        setContext(command);

        return getExecutor().execute(command);
    }

    public final void setApplicationInfoService(
            final ApplicationInfoService service) {
        serviceApplicationInfo = service;
    }

    public final void setArmorDAO(final ArmorDAO dao) {
        daoArmor = dao;
    }

    public final void setFactionDAO(final FactionDAO dao) {
        daoFaction = dao;
    }

    public final void setLayoutService(final LayoutService service) {
        serviceLayout = service;
    }

    public final void setLocalizationService(
            final PunkapocalypticLocalizationService service) {
        serviceLocalization = service;
    }

    public final void setRuleSetService(
            final PunkapocalypticRulesetService service) {
        serviceRuleSet = service;
    }

    public final void setSpecialRuleDAO(final RulesetDAO dao) {
        daoSpecialRule = dao;
    }

    public final void setUnitDAO(final UnitDAO dao) {
        daoUnit = dao;
    }

    public final void setWeaponDAO(final WeaponDAO dao) {
        daoWeapon = dao;
    }

    private final CommandExecutor getExecutor() {
        return executor;
    }

    private final void setContext(final Object command) {
        if (command instanceof ApplicationInfoServiceAware) {
            ((ApplicationInfoServiceAware) command)
                    .setApplicationInfoService(getApplicationInfoService());
        }

        if (command instanceof LocalizationServiceAware) {
            ((LocalizationServiceAware) command)
                    .setLocalizationService(getLocalizationService());
        }

        if (command instanceof LayoutServiceAware) {
            ((LayoutServiceAware) command).setLayoutService(getLayoutService());
        }

        if (command instanceof RulesetServiceAware) {
            ((RulesetServiceAware) command)
                    .setRulesetService(getPunkapocalypticRuleSetService());
        }

        if (command instanceof ArmorDAOAware) {
            ((ArmorDAOAware) command).setArmorDAO(getArmorDAO());
        }

        if (command instanceof FactionDAOAware) {
            ((FactionDAOAware) command).setFactionDAO(getFactionDAO());
        }

        if (command instanceof RulesetDAOAware) {
            ((RulesetDAOAware) command).setRulesetDAO(getSpecialRuleDAO());
        }

        if (command instanceof UnitDAOAware) {
            ((UnitDAOAware) command).setUnitDAO(getUnitDAO());
        }

        if (command instanceof WeaponDAOAware) {
            ((WeaponDAOAware) command).setWeaponDAO(getWeaponDAO());
        }
    }

    protected final ApplicationInfoService getApplicationInfoService() {
        return serviceApplicationInfo;
    }

    protected final ArmorDAO getArmorDAO() {
        return daoArmor;
    }

    protected final FactionDAO getFactionDAO() {
        return daoFaction;
    }

    protected final LayoutService getLayoutService() {
        return serviceLayout;
    }

    protected final PunkapocalypticLocalizationService getLocalizationService() {
        return serviceLocalization;
    }

    protected final PunkapocalypticRulesetService
            getPunkapocalypticRuleSetService() {
        return serviceRuleSet;
    }

    protected final RulesetDAO getSpecialRuleDAO() {
        return daoSpecialRule;
    }

    protected final UnitDAO getUnitDAO() {
        return daoUnit;
    }

    protected final WeaponDAO getWeaponDAO() {
        return daoWeapon;
    }

}