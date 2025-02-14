$(document).ready(function () {
    var nonSortableIndexes = $('th.no-sort').map(function () {
        return $(this).index();
    }).get();

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
                targets: "no-search",
                searchable: false,
            },
            {
                targets: "searchable",
                searchable: true,
            },
            {
                targets: nonSortableIndexes,
                orderable: false,
            }
        ],
        order: []
    });
});