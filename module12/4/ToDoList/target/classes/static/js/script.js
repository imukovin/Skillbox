$(function(){
    //add task
    $('#add-task').click(function(){
        var data = $('#list-form form').serialize();
        $.ajax({
            method: "POST",
            url: "/addtask",
            data: data
        });
        window.location.reload();
    });

    //del task
    $(document).on('click', '.del-link',function(){
        var id = $(this).parent().attr("id");
        $.ajax({
            method: "POST",
            url: "/list/" + id
        });
        window.location.reload();
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