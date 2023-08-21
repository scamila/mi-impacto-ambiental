package domain.organizacion.adapters;

import domain.organizacion.Medicion;

import java.io.IOException;
import java.util.List;

public interface AdapterCargaActividades {
     List<Medicion> cargaActividades(String rutaAlArchivo) throws IOException;
}
