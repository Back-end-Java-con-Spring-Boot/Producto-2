document.addEventListener("DOMContentLoaded", function () {
    const selectSucursal = document.getElementById("sucursalSelect");
    const vehiculosContainer = document.getElementById("vehiculosContainer");
    const vehiculos = vehiculosContainer.querySelectorAll(".form-check");

    function filtrarVehiculos() {
        const sucursalId = selectSucursal.value;
        vehiculos.forEach(v => {
            if (!sucursalId || v.getAttribute("data-sucursal") === sucursalId) {
                v.style.display = "block";
            } else {
                v.style.display = "none";
                v.querySelector("input[type=checkbox]").checked = false;
            }
        });
    }


    selectSucursal.addEventListener("change", filtrarVehiculos);

    vehiculos.forEach(v => {
        const checkbox = v.querySelector("input[type=checkbox]");
        checkbox.addEventListener("change", function () {
            if (checkbox.checked) {
                const sucursalId = v.getAttribute("data-sucursal");
                selectSucursal.value = sucursalId;
                filtrarVehiculos();
            }
        });
    });

    filtrarVehiculos();
});