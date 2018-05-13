package punit.tp.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MainTest {
    public static void main(String[] args) {
        try {
            JasperDesign jasperDesign = null;
            JasperPrint jasperPrint = JasperFillManager.fillReport("", new HashMap<>());
            /*new AUnitTest<>(
                    new JasperTestablePrintForm(jasperDesign, jasperPrint),
                    printForm -> printForm.getField("MyField").value()
            ).resultNotNull().resultEquals("Hello");*/
            JasperTestablePrintForm printForm = new JasperTestablePrintForm(jasperDesign, jasperPrint);
            printForm.parse();
            assertEquals("Hello", printForm.getField("MyField").value());
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
