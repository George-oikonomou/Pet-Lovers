 $(document).ready(function () {
    $('.datatable').DataTable({
        paging: true,
        lengthMenu: [5, 10, 25, 50],
        language: {
            paginate: {
                previous: "&laquo; Previous",
                next: "Next &raquo;"
            },
            emptyTable: "No data available",
            lengthMenu: "Show _MENU_ entries",
            search: "Search:",
        },
        responsive: true,
        columnDefs: [
            {
                targets: "no-search",//todo: add class to the column that we don't want to be searchable
                searchable: false,
            },
            {
                targets: "searchable",//todo: add class to the column that we want to be searchable
                searchable: true,
            }
        ]
    });
});
