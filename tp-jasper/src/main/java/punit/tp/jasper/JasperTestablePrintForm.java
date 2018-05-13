package punit.tp.jasper;

import punit.tp.core.PrintFormField;
import punit.tp.core.TestablePrintForm;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.List;

public class JasperTestablePrintForm implements TestablePrintForm<String> {
    private JasperDesign jasperDesign;
    private JasperPrint jasperPrint;

    public JasperTestablePrintForm(JasperDesign jasperDesign, JasperPrint jasperPrint) {
        this.jasperDesign = jasperDesign;
        this.jasperPrint = jasperPrint;
    }

    public void parse() {
        List<JRField> fields = jasperDesign.getFieldsList();
        List<JRPrintPage> pages = jasperPrint.getPages();
        for (JRPrintPage printPage : pages) {
            List<JRPrintElement> printElements = printPage.getElements();
        }
    }

    @Override
    public PrintFormField getField(String id) {
        return null;
    }
}
