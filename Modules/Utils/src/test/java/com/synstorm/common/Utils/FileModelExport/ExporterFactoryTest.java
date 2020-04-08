package com.synstorm.common.Utils.FileModelExport;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by human-research on 2019-01-28.
 */
public class ExporterFactoryTest {
    @Test
    public void matchExporterStrings() {
        assertTrue(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{aaa,bbb}"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{aaa,bbb}}"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{{aaa,bbb}"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporteraaa,bbb{{{"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter[a,b,c}"));
        assertTrue(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{aaa}"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{}{ab,bc}"));
        assertFalse(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{[]abc,abc,abc}"));
        assertTrue(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{aaa,b,c}"));
        assertTrue(ExporterFactory.INSTANCE.matchExporterString("CsvChemicalEventExporter{_}"));
    }
}