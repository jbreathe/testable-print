Usage example:

```
InputStream is = SomeTest.class.getResourceAsStream("/Some.jrxml");
JasperReport jasperReport = JasperCompileManager.compileReport(is);
Map<String, Object> params = new HashMap<>();
params.put("SongName", "I believe I can fly!");
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
JasperTestablePrintForm printForm = new JasperTestablePrintForm(jasperReport, jasperPrint);
assertEquals("I believe I can fly!", printForm.field("SongName").value());
```
