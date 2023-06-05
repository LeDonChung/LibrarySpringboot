$('document').ready(function() {
    $('table #btnUpdateProduct').on('click', function(e) {
        e.preventDefault();

        let href = $(this).attr('href');
        $.get(href, function(product, status) {
           // val
            $('#editId').val(product.id);
            $('#editProductName').val(product.productName);
            $('#editCostPrice').val(product.costPrice);
            $('#editSalePrice').val(product.salePrice);
            $('#editDescription').val(product.description);
            $('#editQuantity').val(product.quantity);
            $('#imgProduct').attr("src", "")
            let imageProduct =
                `<img src="data:image/jpeg;base64,${product.image}" name="image" alt="" style="height: 40px; width: 40px;">`;
            $('#editCategories').val(product.category.id);
            $('#editCategories').parent().append(imageProduct);
        });

        $('#updateProduct').modal('show');
    })
})