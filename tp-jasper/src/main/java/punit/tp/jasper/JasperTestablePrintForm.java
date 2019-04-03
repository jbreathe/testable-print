package punit.tp.jasper;

import net.sf.jasperreports.engine.*;
import punit.tp.core.PrintFormField;
import punit.tp.core.TestablePrintForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JasperTestablePrintForm implements TestablePrintForm<String> {
    private final JasperReport jasperReport;
    private final JasperPrint jasperPrint;

    private Map<UUID, String> associations = new HashMap<>();

    public JasperTestablePrintForm(JasperReport jasperReport, JasperPrint jasperPrint) {
        this.jasperReport = jasperReport;
        this.jasperPrint = jasperPrint;
        parse();
    }

    @Override
    public PrintFormField field(String id) {
        for (JRPrintPage page : jasperPrint.getPages()) {
            for (JRPrintElement element : page.getElements()) {
                // only JRPrintText's supported for now
                if (!(element instanceof JRPrintText)) {
                    continue;
                }
                if (associations.containsKey(element.getUUID())) {
                    String fieldName = associations.get(element.getUUID());
                    if (fieldName.equals(id)) {
                        String fullText = ((JRPrintText) element).getFullText();
                        return new JasperPrintFormField(fullText);
                    }
                }
            }
        }
        return null;
    }

    private void parse() {
        JRBand[] allBands = jasperReport.getAllBands();
        for (JRBand band : allBands) {
            associations.putAll(getAssociations(band));
        }
    }

    private Map<UUID, String> getAssociations(JRBand band) {
        Map<UUID, String> res = new HashMap<>();
        List<JRChild> children = band.getChildren();
        for (JRChild child : children) {
            // only JRTextField's supported for now
            if (!(child instanceof JRTextField)) {
                continue;
            }
            JRTextField textField = (JRTextField) child;
            JRExpressionChunk[] chunks = textField.getExpression().getChunks();
            if (chunks.length < IntVal.ONE) {
                continue;
            }
            res.put(textField.getUUID(), chunks[IntVal.ZERO].getText());
        }
        return res;
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

    private static final class IntVal {
        private static final int ZERO = 0;
        private static final int ONE = 1;

        private IntVal() {
        }
    }
}
