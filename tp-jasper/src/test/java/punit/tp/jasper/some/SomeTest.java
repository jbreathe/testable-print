package punit.tp.jasper.some;

import net.sf.jasperreports.engine.*;
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
        params.put("SongName", "I believe I can fly!");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        JasperTestablePrintForm printForm = new JasperTestablePrintForm(jasperReport, jasperPrint);
        assertEquals("I believe I can fly!", printForm.field("SongName").value());
    }
}
