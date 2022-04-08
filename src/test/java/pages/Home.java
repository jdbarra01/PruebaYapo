package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static java.util.Locale.ROOT;

public class Home {
    private WebDriver driver;
    String file = ROOT + "data.csv";
    String salida = ROOT + "salida.csv";
    String delimiter = ",";
    String line;
    String nameValue = null;

    public Home(WebDriver driver) throws FileNotFoundException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "cb1-edit")
    WebElement txtBusqueda;

    @FindBy(css = ".nav-icon-search")
    WebElement btnBusqueda;

    @FindBy(xpath = "//*[@id=\"root-app\"]/div/div/section/ol/li[1]/div/div/div[1]/a/div/div/div")
    WebElement seleccionProducto;

    @FindBy(css = ".ui-pdp-title")
    WebElement nameProd;

    @FindBy(xpath = "//a[contains(text(),'Mercado Libre Chile - Donde comprar y vender de to')]")
    WebElement volverHome;

    public void busqueda() throws IOException {
        List<List<String>> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                List<String> values = Arrays.asList(line.split(delimiter));
                for (int i = 0; i <= 5; i++) {
                    if (lines.size() == i) {
                        txtBusqueda.sendKeys(line.split(delimiter));
                        btnBusqueda.click();
                        seleccionProducto.click();
                        nameValue = nameProd.getText();
                        volverHome.click();
                        wirterCsv(salida, Collections.singletonList((nameValue.split(delimiter))));
                    }
                }
                lines.add(values);
            }
            lines.forEach(l -> System.out.println(l));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void wirterCsv(String csvFileName, List<String[]> data)
            throws FileNotFoundException, UnsupportedEncodingException {
        File csvOutputFile = new File(csvFileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile, "GBK")) {
            data.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

}



