$('document').ready(() => {
    $('table #btnUpdate').on('click', function(e) {
        e.preventDefault();

        let href = $(this).attr('href');
        $.get(href, function(category, status) {
            let inputId = `<input type="hidden" class="form-control" name="id" value="${category.id}">`;
            $('#idEdit').parent('.form-group').append(inputId);
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.categoryName);
            $('#codeEdit').val(category.categoryCode);
            $('#desEdit').val(category.description);
            console.log(category);
        });
        $('#updateCategory').modal('show');
    });
});