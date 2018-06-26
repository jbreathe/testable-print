package punit.tp.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import punit.tp.core.PrintFormField;
import punit.tp.core.TestablePrintForm;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JasperTestablePrintForm implements TestablePrintForm<String> {
    private final JasperReport jasperReport;
    private final JasperPrint jasperPrint;

    private Map<UUID, String> association = new HashMap<>();

    public JasperTestablePrintForm(JasperReport jasperReport, JasperPrint jasperPrint) {
        this.jasperReport = jasperReport;
        this.jasperPrint = jasperPrint;
        parse();
    }

    @Override
    public PrintFormField field(String id) {
        for (JRPrintPage page: jasperPrint.getPages()) {
            for (JRPrintElement element: page.getElements()) {
                if (association.containsKey(element.getUUID())) {
                    String fieldName = association.get(element.getUUID());
                    if (fieldName.equals(id)) {
                        String fullText = ((JRTemplatePrintText) element).getFullText();
                        return new JasperPrintFormField(fullText);
                    }
                }
            }
        }
        return null;
    }

    private void parse() {
        for (JRChild child: jasperReport.getTitle().getChildren()) {
            JRBaseTextField textField = (JRBaseTextField) child;
            association.put(
                    textField.getUUID(),
                    textField.getExpression().getChunks()[0].getText()
            );
        }
    }

    private class JasperPrintFormField implements PrintFormField {
        private final String value;

        private JasperPrintFormField(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }
    }
}
