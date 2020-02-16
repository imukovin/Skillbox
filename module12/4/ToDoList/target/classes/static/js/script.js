$(function(){
    //add task
    $('#add-task').click(function(){
        var data1 = $('#list-form form').serialize();
        $.ajax({
            method: "POST",
            url: "/addtask",
            data: data1,
            statusCode:{
                510:function(){
                    alert('Запиь не добавлена!');
                }
            }
        });
    });

    //del task
    $(document).on('click', '.del-link',function(){
        var id = $(this).parent().attr("id");
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', 'http://localhost:8080/list/' + id, true);
        xhr.onreadystatechange = () => {
                if (xhr.readyState === 4) {
                    this.parentElement.parentElement.removeChild(this.parentElement);
                }
              }
        xhr.send();
    });

    //mark as completed
    $(document).on('click', '.compl-link',function(){
        var id = $(this).parent().attr("id");
        $.ajax({
            method: "POST",
            url: "/listCompl/" + id
        });
        window.location.reload();
    });
});