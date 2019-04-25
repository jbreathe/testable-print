package punit.tp.jasper.some;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.junit.Test;
import punit.tp.jasper.JasperTestablePrintForm;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SomeTest {
    @Test
    public void simpleTest() throws JRException {
        InputStream is = SomeTest.class.getResourceAsStream("/Some.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(is);
        Map<String, Object> params = new HashMap<>();
        params.put("Performer", "Enter Shikari");
        params.put("Album", "Common Dreads");
        params.put("SongName", "Hectic");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        JasperTestablePrintForm printForm = new JasperTestablePrintForm(jasperReport, jasperPrint);
        assertEquals("Enter Shikari", printForm.field("Performer").value());
        assertEquals("Common Dreads", printForm.field("Album").value());
        assertEquals("Hectic", printForm.field("SongName").value());
    }
}
