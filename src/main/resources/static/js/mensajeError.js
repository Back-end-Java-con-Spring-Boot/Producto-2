document.addEventListener('DOMContentLoaded', function () {

    const errorMessages = document.querySelectorAll('#auto-close');

    errorMessages.forEach(function(errorDiv) {

        setTimeout(function () {

            errorDiv.style.transition = "opacity 0.3s ease";
            errorDiv.style.opacity = 0;

            setTimeout(() => errorDiv.remove(), 500);

        }, 5000);

    });

});