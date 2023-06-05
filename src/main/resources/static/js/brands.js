$('document').ready(() => {
    $('table #btnUpdate').on('click', function(e) {
        e.preventDefault();

        let href = $(this).attr('href');
        $.get(href, function(brand, status) {
            let inputId = `<input type="hidden" class="form-control" name="id" value="${brand.id}">`;
            $('#idEdit').parent('.form-group').append(inputId);
            $('#idEdit').val(brand.id);
            $('#nameEdit').val(brand.name);
            $('#codeEdit').val(brand.code);
        });
        $('#updateBrand').modal('show');
    });
});