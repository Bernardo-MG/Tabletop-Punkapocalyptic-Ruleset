package com.wandrell.tabletop.testing.punkapocalyptic.framework.test.unit.ruleset;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wandrell.tabletop.punkapocalyptic.model.inventory.Equipment;
import com.wandrell.tabletop.punkapocalyptic.model.inventory.Weapon;
import com.wandrell.tabletop.punkapocalyptic.model.unit.GroupedUnit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.Unit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.UnitTemplate;
import com.wandrell.tabletop.punkapocalyptic.model.unit.mutation.MutantUnit;
import com.wandrell.tabletop.punkapocalyptic.model.unit.mutation.Mutation;
import com.wandrell.tabletop.punkapocalyptic.service.DefaultRulesetService;
import com.wandrell.tabletop.punkapocalyptic.service.RulesetService;
import com.wandrell.tabletop.valuebox.ValueBox;

public final class TestUnitValoration {

    private RulesetService service;

    public TestUnitValoration() {
        super();
    }

    @SuppressWarnings("unchecked")
    @BeforeClass
    public final void initializeWeapons() {
        final Map<Object, Object> config;

        config = Mockito.mock(Map.class);

        service = new DefaultRulesetService(config);
    }

    @Test
    public final void testValoration_BaseUnit() throws Exception {
        Assert.assertEquals(service.getUnitValoration(getUnit()), (Integer) 15);
    }

    @Test
    public final void testValoration_GroupedUnit() throws Exception {
        Assert.assertEquals(service.getUnitValoration(getGroupedUnit()),
                (Integer) 30);
    }

    @Test
    public final void testValoration_MutantUnit() throws Exception {
        Assert.assertEquals(service.getUnitValoration(getMutantUnit()),
                (Integer) 28);
    }

    private final GroupedUnit getGroupedUnit() {
        final GroupedUnit unit;
        final UnitTemplate template;
        final Collection<Equipment> equipment;
        final Collection<Weapon> weapons;
        final ValueBox size;
        Equipment equip;
        Weapon weapon;

        equipment = new LinkedList<>();
        weapons = new LinkedList<>();

        unit = Mockito.mock(GroupedUnit.class);

        template = Mockito.mock(UnitTemplate.class);

        Mockito.when(template.getBaseCost()).thenReturn(1);

        Mockito.when(unit.getUnitTemplate()).thenReturn(template);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(2);

        weapons.add(weapon);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(3);

        weapons.add(weapon);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(4);

        equipment.add(equip);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(5);

        equipment.add(equip);

        size = Mockito.mock(ValueBox.class);
        Mockito.when(size.getValue()).thenReturn(2);

        Mockito.when(unit.getEquipment()).thenReturn(equipment);
        Mockito.when(unit.getWeapons()).thenReturn(weapons);
        Mockito.when(unit.getGroupSize()).thenReturn(size);

        return unit;
    }

    private final MutantUnit getMutantUnit() {
        final MutantUnit unit;
        final UnitTemplate template;
        final Collection<Equipment> equipment;
        final Collection<Weapon> weapons;
        final Collection<Mutation> mutations;
        Mutation mutation;
        Equipment equip;
        Weapon weapon;

        equipment = new LinkedList<>();
        weapons = new LinkedList<>();
        mutations = new LinkedList<>();

        unit = Mockito.mock(MutantUnit.class);

        template = Mockito.mock(UnitTemplate.class);

        Mockito.when(template.getBaseCost()).thenReturn(1);

        Mockito.when(unit.getUnitTemplate()).thenReturn(template);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(2);

        weapons.add(weapon);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(3);

        weapons.add(weapon);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(4);

        equipment.add(equip);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(5);

        equipment.add(equip);

        mutation = Mockito.mock(Mutation.class);
        Mockito.when(mutation.getCost()).thenReturn(6);

        mutations.add(mutation);

        mutation = Mockito.mock(Mutation.class);
        Mockito.when(mutation.getCost()).thenReturn(7);

        mutations.add(mutation);

        Mockito.when(unit.getEquipment()).thenReturn(equipment);
        Mockito.when(unit.getWeapons()).thenReturn(weapons);
        Mockito.when(unit.getMutations()).thenReturn(mutations);

        return unit;
    }

    private final Unit getUnit() {
        final Unit unit;
        final UnitTemplate template;
        final Collection<Equipment> equipment;
        final Collection<Weapon> weapons;
        Equipment equip;
        Weapon weapon;

        equipment = new LinkedList<>();
        weapons = new LinkedList<>();

        unit = Mockito.mock(Unit.class);

        template = Mockito.mock(UnitTemplate.class);

        Mockito.when(template.getBaseCost()).thenReturn(1);

        Mockito.when(unit.getUnitTemplate()).thenReturn(template);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(2);

        weapons.add(weapon);

        weapon = Mockito.mock(Weapon.class);
        Mockito.when(weapon.getCost()).thenReturn(3);

        weapons.add(weapon);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(4);

        equipment.add(equip);

        equip = Mockito.mock(Equipment.class);
        Mockito.when(equip.getCost()).thenReturn(5);

        equipment.add(equip);

        Mockito.when(unit.getEquipment()).thenReturn(equipment);
        Mockito.when(unit.getWeapons()).thenReturn(weapons);

        return unit;
    }

}
