$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
			$.each(data.result, function (index, file) {
                $("#uploadImg").attr("src",file.fileUrl);
			});
        }
    });
});