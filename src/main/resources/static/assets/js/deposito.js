function transferir()
{
    var nombre = document.getElementById('Nombre');
    var datos = document.getElementById('Datos');

    let opciones = document.getElementsByName('contacto');
    let id;
    
    // Recorremos cada radiobutton
    for (let i = 0; i < opciones.length; i++) {
      if (opciones[i].checked) {
        // Si el radiobutton está seleccionado, retornamos su valor
        id= opciones[i].value;
      }
    }

    //alert(document.getElementById('name_'+id).innerHTML);
    document.getElementById('Nombre').value=document.getElementById('name_'+id).innerHTML;
    document.getElementById('Datos').value=document.getElementById('detalle_'+id).innerHTML;
}


function validarRUT(data) {
    
    var rut = data.value;
    var valor = rut.replace(/[.-]/g, '').toUpperCase();
    
    var cuerpo = valor.slice(0, -1);
    var dv = valor.slice(-1).toUpperCase();
  
    // Asegurar que el cuerpo sea numérico y el DV no sea una secuencia inválida
    if(cuerpo.length < 7 || isNaN(cuerpo) || dv == '0') {
      data.value="";
      alert('rut Invalido')
    }
  
    // Calcular DV
    var suma = 0;
    var multiplo = 2;
  
    // Recorrer de atrás hacia adelante
    for(var i=1;i<=cuerpo.length;i++) {
      // Obtener su producto con el múltiplo correspondiente
      var index = multiplo * valor.charAt(cuerpo.length - i);
      suma = suma + index;
      if(multiplo < 7) {
        multiplo = multiplo + 1;
      } else {
        multiplo = 2;
      }
    }
  
    // Calcular dígito verificador en base a la suma obtenida
    var dvEsperado = 11 - (suma % 11);
    dv = (dv == 'K')?10:dv;
    dv = (dv == 0)?11:dv;
  
    // Comparar el DV (con la lógica de 'K' y 0)
    if(dvEsperado != dv) {
        data.value="";
        alert('rut Invalido')
    }
  
    // Si todo es correcto
    return true;
  }