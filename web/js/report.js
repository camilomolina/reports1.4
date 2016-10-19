$(document).ready(function () {
    Report.init();
});

var PARAMETER_TYPE_ALFANUMERIC = 1;
var PARAMETER_TYPE_DATE = 2;
var PARAMETER_TYPE_NUMERIC = 3;
var PARAMETER_TYPE_BOOLEAN = 4;
var PARAMETER_TYPE_DATE_RANGE = 5;
var PARAMETER_TYPE_ALFANUMERIC_PLUS_LIKE = 6;

var ERROR_MESSAGE = [];
var Report = {
    init: function () {
        $("input[type=button]").button().click(function (event) {
            event.preventDefault();
        });

        $("input[id^='parameter_']").each(function (i, el) {
            var data1 = $(el).attr("parameterData1");
            var data2 = $(el).attr("parameterData2");

            if ($(el).attr("parameterType") == PARAMETER_TYPE_DATE) {
                //dateFormat: "yy-mm-dd"
                $(el).datepicker({
                    dateFormat: data2 == 2 ? "mm/yy" : (data2 == 3 ? "yy" : "dd/mm/yy")
                });
            } else if ($(el).attr("parameterType") == PARAMETER_TYPE_DATE_RANGE) {
                var parameterNameInit = $(el).attr("id");
                var index = parameterNameInit.indexOf("_init");
                if (index > 0) {
                    var parameterName = parameterNameInit.substring(0, parameterNameInit.length - 5);
                    var maxDate = "";
                    if (data1 != null && data1 != "") {
                        maxDate = "+" + data1 + "d";
                    }

                    $("#"+parameterName+"_init").datepicker({
                        dateFormat: data2 == 2 ? "mm/yy" : (data2 == 3 ? "yy" : "dd/mm/yy"),
                        maxDate: maxDate,
                        onClose: function(text, obj) {
                            $("#"+parameterName+"_final").datepicker( "option", "minDate", text );
                        },
                        onSelect: function(text, obj){
                            $("#"+parameterName+"_final").val("");
                        }
                    });
                    $("#"+parameterName+"_final").datepicker({
                        dateFormat: data2 == 2 ? "mm/yy" : (data2 == 3 ? "yy" : "dd/mm/yy"),
                        maxDate: maxDate,
                        onClose: function(text, obj) {
                            $("#"+parameterName+"_init").datepicker( "option", "maxDate", text );
                        }
                    });
                }
            }
        });
    },
    validate: function() {
        var forward = true;
        ERROR_MESSAGE = [];

        $("input[id^='parameter_']").each(function (i, el) {
            if ($(el).attr("parameterRequired") == 'true') {
                // validamos los atributos obligatorios
                if ($(el).attr("parameterType") == PARAMETER_TYPE_ALFANUMERIC || $(el).attr("parameterType") == PARAMETER_TYPE_ALFANUMERIC_PLUS_LIKE) {
                    if ($(el).val() == "") {
                        ERROR_MESSAGE.push("Debe ingresar una dato para " + $(el).attr("parameterName"));
                        forward = false;
                    }
                } else if ($(el).attr("parameterType") == PARAMETER_TYPE_DATE) {
                    if ($(el).val() == "") {
                        ERROR_MESSAGE.push("Debe ingresar una fecha para " + $(el).attr("parameterName"));
                        forward = false;
                    }
                } else if ($(el).attr("parameterType") == PARAMETER_TYPE_NUMERIC) {
                    if ($(el).val() == "" || isNaN($(el).val())) {
                        ERROR_MESSAGE.push("Debe ingresar un numero para " + $(el).attr("parameterName"));
                        forward = false;
                    }
                } else if ($(el).attr("parameterType") == PARAMETER_TYPE_DATE_RANGE) {
                    if ($(el).val() == "") {
                        ERROR_MESSAGE.push("Debe ingresar una fecha para el rango " + $(el).attr("parameterName"));
                        forward = false;
                    }
                }
            } else {
                // validamos que los datos no obligatorios pero ingresados tengan los formatos correctos
                if ($(el).attr("parameterType") == PARAMETER_TYPE_NUMERIC) {
                    if ($(el).val() != "" && isNaN($(el).val())) {
                        ERROR_MESSAGE.push("Debe ingresar un numero valido para " + $(el).attr("parameterName"));
                        forward = false;
                    }
                }
            }
        });

        return forward;
    },
    generate: function () {
        if (Report.validate()) {
            $("#dialogBase64").dialog({
                title: "Mensaje",
                modal: true,
                resizable: false,
                closeOnEscape: false,
                closeText: false,
                draggable: false,
                open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }
            });

            $("#method").val("generateBase64");

            /*
            $(document).on("submit", "form.fileDownloadForm", function (e) {
                $.fileDownload($(this).prop('action'), {
                    preparingMessageHtml: "We are preparing your report, please wait...",
                    failMessageHtml: "There was a problem generating your report, please try again.",
                    httpMethod: "POST",
                    data: $(this).serialize()
                });
                e.preventDefault(); //otherwise a normal form submit would occur
            });
*/
            $.ajax({
                url: "/reports14/report.do",
                method: "POST",
                data: $("#reportForm").serialize(),
                dataType: "json",
                success: function (json) {
                    $("#dialogBase64").dialog("close");

                    if (json.responseType == 1) {
                        $("#method").val("generate");
                        //$("#report").val(json.o);
                        $("#reportForm").submit();
                    } else {
                        Generic.errorDialogMsg(json.message);
                    }
                }
            });

//            $("#method").val("generate");
//            $("#reportForm").submit();
        } else {
            Generic.errorDialog(ERROR_MESSAGE);
        }
    }
}


