package domain.organizacion.adapters;

import domain.organizacion.Medicion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AdapterCargaExcel implements AdapterCargaActividades {


    public Medicion obtenerMedicion(Row row){
        Iterator<Cell> cellIterator = row.cellIterator();

        Cell cell = cellIterator.next();
        Medicion medicion = new Medicion();

        // Actividad
        medicion.setActividad(cell.getStringCellValue().toLowerCase());

        cell = cellIterator.next();

        // Tipo de consumo
        medicion.setTipoDeConsumo(cell.getStringCellValue().toLowerCase());

        cell = cellIterator.next();

        //Valor
        switch (cell.getCellType()){
            case STRING:
                medicion.setValor(cell.getStringCellValue());
                break;
            case NUMERIC:
                medicion.setValor(String.valueOf(cell.getNumericCellValue()));
                break;
        }
        cell = cellIterator.next();

        // Periodicidad
        medicion.setPeriodicidad(cell.getStringCellValue().toLowerCase());
        cell = cellIterator.next();

        // Periodo de imputacion
/*         if(cell.getCellType() == CellType.STRING){
            medicion.setPeriodoDeImputacion(cell.getStringCellValue().toLowerCase());
        }
        else {
            SimpleDateFormat formato = new SimpleDateFormat("MMYYYY");
            medicion.setPeriodoDeImputacion(cell.getCellType().toString());
        } */

        if(medicion.getPeriodicidad().equals("mensual")){
            SimpleDateFormat formato = new SimpleDateFormat("MMYYYY");
            medicion.setPeriodoDeImputacion(formato.format(cell.getDateCellValue()));
        }else{
            SimpleDateFormat formato = new SimpleDateFormat("YYYY");
            medicion.setPeriodoDeImputacion(formato.format(cell.getDateCellValue()));
        }

        return medicion;
    }

    public List<Medicion> cargaActividades(String rutaAlArchivo) throws IOException {

        List<Medicion> mediciones = new ArrayList<>();

        FileInputStream file = new FileInputStream(rutaAlArchivo);

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();
        row = rowIterator.next();// Para Evitar la segunda linea que tiene los titulos
        do{
            row = rowIterator.next();
            //Obtengo la medicion de la linea
            Medicion medicion = this.obtenerMedicion(row);
            mediciones.add(medicion);
        }while (rowIterator.hasNext());
        workbook.close();
        return mediciones;
    }

}
