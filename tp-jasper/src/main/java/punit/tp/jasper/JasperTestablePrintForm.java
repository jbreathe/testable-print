package punit.tp.jasper;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import punit.tp.core.PrintFormField;
import punit.tp.core.TestablePrintForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JasperTestablePrintForm implements TestablePrintForm<String> {
    private final Map<String, JRPrintText> fields;

    public JasperTestablePrintForm(JasperReport jasperReport, JasperPrint jasperPrint) {
        this.fields = parse(jasperReport, jasperPrint);
    }

    @Override
    public PrintFormField field(String id) {
        if (fields.containsKey(id)) {
            return new JasperPrintFormField(fields.get(id).getFullText());
        }
        return null;
    }

    private Map<String, JRPrintText> parse(JasperReport jasperReport, JasperPrint jasperPrint) {
        Map<UUID, String> associations = new HashMap<>();
        for (JRBand band : jasperReport.getAllBands()) {
            associations.putAll(getAssociations(band));
        }
        Map<String, JRPrintText> fields = new HashMap<>();
        for (JRPrintPage page : jasperPrint.getPages()) {
            for (JRPrintElement element : page.getElements()) {
                // only JRPrintText's supported for now
                if (!(element instanceof JRPrintText)) {
                    continue;
                }
                if (associations.containsKey(element.getUUID())) {
                    String fieldName = associations.get(element.getUUID());
                    fields.put(fieldName, (JRPrintText) element);
                }
            }
        }
        return fields;
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
