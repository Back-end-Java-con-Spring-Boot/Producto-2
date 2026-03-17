function activarEdicion(id) {
    const row = document.getElementById('row-' + id);
    row.querySelectorAll('.view-mode').forEach(el => el.classList.add('d-none'));
    row.querySelectorAll('.edit-mode').forEach(el => el.classList.remove('d-none'));
}


function cancelarEdicion(id) {
    const row = document.getElementById('row-' + id);
    row.querySelectorAll('.view-mode').forEach(el => el.classList.remove('d-none'));
    row.querySelectorAll('.edit-mode').forEach(el => el.classList.add('d-none'));
}


function enviarEdicion(id) {

    const nombre = document.getElementById('edit-nombre-' + id).value;
    const direccion = document.getElementById('edit-direccion-' + id).value;
    const telefono = document.getElementById('edit-telefono-' + id).value;
    const ciudad = document.getElementById('edit-ciudad-' + id).value;

    document.getElementById('form-edit-id').value = id;
    document.getElementById('form-edit-nombre').value = nombre;
    document.getElementById('form-edit-direccion').value = direccion;
    document.getElementById('form-edit-telefono').value = telefono;
    document.getElementById('form-edit-ciudad').value = ciudad;

    document.getElementById('formInlineEdit').submit();
}