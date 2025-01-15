function handleAdoptionRequestsOnLoad() {
    const notifiedAdoptions = JSON.parse(sessionStorage.getItem('notifiedAdoptions')) || {};
    const now = Date.now();

    document.querySelectorAll('.adoption-request-row').forEach(function(row) {
        const adoptionRequestId = row.getAttribute('data-id');

        if (notifiedAdoptions[adoptionRequestId] && now - notifiedAdoptions[adoptionRequestId] < 43200000) {
            const button = row.querySelector('button[type="submit"]');
            if (button) {
                button.disabled = true;
                button.style.backgroundColor = "#d3d3d3";
                button.innerText = "Notified";
            }
        }
    });
}

function disableButton(button) {
    const adoptionRequestId = button.closest('form').getAttribute('action').split('/')[3];

    const notifiedAdoptions = JSON.parse(sessionStorage.getItem('notifiedAdoptions')) || {};
    notifiedAdoptions[adoptionRequestId] = Date.now();
    sessionStorage.setItem('notifiedAdoptions', JSON.stringify(notifiedAdoptions));

    button.disabled = true;
    button.style.backgroundColor = "#d3d3d3";
    button.innerText = "Notified";

    button.closest('form').submit();
}
