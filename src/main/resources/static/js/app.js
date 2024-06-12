$(function () {
    $('.js-confirm').on('click', function () {
        return confirm("Потвърждаваш ли действието?");
    });

    $('[data-toggle="tooltip"]').tooltip();
})