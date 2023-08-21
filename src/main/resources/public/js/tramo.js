var selectTransporte = document.getElementById("transporte-select");
let subte = document.getElementById("subte");
let colectivo = document.getElementById("colectivo");
let tren = document.getElementById("tren");
let selectCombustible = document.getElementById("combustible-select");
let inputServicio = document.getElementById("servicio-contratado");
let checkCompartido = document.getElementById("compartido-check");
let btnAdd = document.getElementById("agregar-btn");

selectTransporte.addEventListener("change",function(){
    var opcion = selectTransporte.value;

    switch(opcion){
        case "TREN":
            setearDirecciones(true);
            ocultarTransportePublico();
            tren.classList.remove("d-none");
            ocultarParametrosParticular
            break;
        case "SUBTE":
            setearDirecciones(true); 
            ocultarTransportePublico();
            subte.classList.remove("d-none");
            ocultarParametrosParticular();
            break;
        case "COLECTIVO":
            setearDirecciones(true);
            ocultarTransportePublico();
            colectivo.classList.remove("d-none");
            ocultarParametrosParticular();
            break;
        case "MOTO": case "AUTO": case "CAMIONETA":
            ocultarTransportePublico();
            setearDirecciones(false);
            selectCombustible.disabled = false;
            inputServicio.disabled = true;
            checkCompartido.disabled = false;
            break;
        case "SERVICIO_CONTRATADO":
            ocultarTransportePublico();
            setearDirecciones(false);
            selectCombustible.disabled = true;
            inputServicio.disabled = false;
            checkCompartido.disabled = false;
            break;
        default:
            ocultarTransportePublico();
            setearDirecciones(false);
            selectCombustible.disabled = true;
            inputServicio.disabled = true;
            ocultarParametrosCheck();
    }
})

function setearDirecciones(estado){
    let inputPartida  = document.getElementById("tramo-partida").getElementsByTagName("input");
    let inputDestino = document.getElementById("tramo-destino").getElementsByTagName("input");
    for(let input of inputPartida){
        input.disabled = estado;
    }
    for(let input of inputDestino){
        input.disabled = estado;
    }
}

function ocultarParametrosParticular(){
    selectCombustible.disabled =true;
    inputServicio.disabled = true;
    ocultarParametrosCheck();
}

function ocultarTransportePublico(){
    tren.classList.add("d-none");
    subte.classList.add("d-none");
    colectivo.classList.add("d-none");
}

function ocultarParametrosCheck(){
    checkCompartido.disabled = true;
    checkCompartido.checked = false;
    btnAdd.classList.add("d-none");
    listaMiembros.replaceChildren();
    acompaniantes = 1;
}


checkCompartido.addEventListener("change",function(){
    if(this.checked){
        btnAdd.classList.remove("d-none");
    }
    else{
        btnAdd.classList.add("d-none");
        listaMiembros.replaceChildren();
        acompaniantes = 1;
    }
})

const exampleModal = document.getElementById('exampleModal')
exampleModal.addEventListener('show.bs.modal', event => {
  const modalBodyInput = exampleModal.querySelector('.modal-body input')
  modalBodyInput.value = ""
})

let listaMiembros = document.getElementById("miembros");
var acompaniantes = 1;
const nuevoDiv = (id) =>{
    let nuevo = document.createElement("div")
    nuevo.className = "input-group";

    let labelNuevo = document.createElement("span")
    labelNuevo.className= "input-group-text col-md-2";
    labelNuevo.textContent = "ID acompañante "+acompaniantes+": ";

    let inputNuevo = document.createElement("input")
    inputNuevo.className = "form-control col-md-2";
    inputNuevo.readOnly = true;
    inputNuevo.value = id;
    inputNuevo.name = "otroMiembro";

    nuevo.appendChild(labelNuevo);
    nuevo.appendChild(inputNuevo);

    acompaniantes++;
    return nuevo;
}

function agregarMiembro() {
    var id = document.getElementById("id-acompaniante").value;
    let bloque = nuevoDiv(id);
    listaMiembros.appendChild(bloque);
}

//---------------
let subteSelect = document.getElementById("select-subte");
let paradaSubte = document.getElementsByClassName("p-subte");

let trenSelect = document.getElementById("select-tren");
let paradaTren = document.getElementsByClassName("p-tren");

let busSelect = document.getElementById("select-bus");
let paradaBus = document.getElementsByClassName("p-bus");

function borrarOpciones(lista){
    for(let opcion of lista){
        opcion.classList.add("d-none");
    }
}

subteSelect.addEventListener("change",function(){
    borrarOpciones(paradaSubte);
    var ind = subteSelect.selectedIndex - 1;
    let opciones = document.getElementsByClassName("s-"+ind);
    for(let item of opciones){
        item.classList.remove("d-none");
    }
});

trenSelect.addEventListener("change",function(){
    borrarOpciones(paradaTren);
    var ind = trenSelect.selectedIndex - 1;
    let opciones = document.getElementsByClassName("t-"+ind);
    for(let item of opciones){
        item.classList.remove("d-none");
    }
});

busSelect.addEventListener("change",function(){
    borrarOpciones(paradaBus);
    var ind = busSelect.selectedIndex - 1;
    let opciones = document.getElementsByClassName("b-"+ind);
    for(let item of opciones){
        item.classList.remove("d-none");
    }
});
