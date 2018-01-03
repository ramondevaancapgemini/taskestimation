$(document).ready(function() {
//    $('.ui.selection.dropdown').dropdown();
//    $('.ui.menu .ui.dropdown').dropdown({
//        on: 'hover'
//    });
    $('.ui.dropdown').dropdown();
    $(".goto").click(function() {
        window.location = $(this).find("a").attr("href");
        return false;
    });
});