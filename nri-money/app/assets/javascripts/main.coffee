$(document).ready ->
  $('.dropdown-menu li a').click ->
    $(this).parents('.input-group-btn').find('.btn').html $(this).text() + ' <span class="caret"></span>'
    $(this).parents('.input-group-btn').find('.btn').val $(this).data('value')
   
    $("#fromCurrency").val $(this).data('value')
    return
  return