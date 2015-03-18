package com.wandrell.tabletop.punkapocalyptic.service.file.command;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.DRField;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.expression.Expressions;

import com.wandrell.pattern.command.ReturnCommand;
import com.wandrell.tabletop.punkapocalyptic.conf.ReportBundleConf;
import com.wandrell.tabletop.punkapocalyptic.conf.ReportConf;
import com.wandrell.tabletop.punkapocalyptic.conf.factory.DynamicReportsFactory;
import com.wandrell.tabletop.punkapocalyptic.model.inventory.Equipment;
import com.wandrell.tabletop.punkapocalyptic.report.datatype.EquipmentDataType;
import com.wandrell.tabletop.punkapocalyptic.service.LocalizationService;
import com.wandrell.tabletop.punkapocalyptic.util.tag.service.LocalizationServiceAware;

public final class BuildUnitEquipmentSubreportCommand implements
        ReturnCommand<ComponentBuilder<?, ?>>, LocalizationServiceAware {

    private LocalizationService localizationService;

    public BuildUnitEquipmentSubreportCommand() {
        super();
    }

    @Override
    public final ComponentBuilder<?, ?> execute() {
        final SubreportBuilder subreport;

        subreport = getEquipmentSubreport();
        subreport.setDataSource(Expressions
                .subDatasourceBeanCollection(ReportConf.EQUIPMENT));

        return DynamicReportsFactory.getInstance()
                .getBorderedCellComponentThin(subreport);
    }

    @Override
    public final void setLocalizationService(final LocalizationService service) {
        localizationService = service;
    }

    private final SubreportBuilder getEquipmentSubreport() {
        final JasperReportBuilder report;
        final DRField<Equipment> field;

        field = new DRField<Equipment>(ReportConf.CURRENT, Equipment.class);
        field.setDataType(new EquipmentDataType());

        report = DynamicReports.report();
        report.detail(Components.horizontalList(Components.horizontalGap(10),
                Components.verticalList(Components.text(field))));
        report.title(Components.text(getLocalizationService().getReportString(
                ReportBundleConf.EQUIPMENT)));

        return Components.subreport(report);
    }

    private final LocalizationService getLocalizationService() {
        return localizationService;
    }

}