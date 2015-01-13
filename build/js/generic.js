var Generic = {
    errorDialog: function (errorMessage) {
        var error = "<ul>";
        for (var i = 0; i < errorMessage.length; i++) {
            error += "<li>" + errorMessage[i] + "</li>";
        }
        error += "</ul>";

        var div = "<div>" + error + "<br></div>";
        $(div).dialog({
            title: "Advertencia",
            modal: true,
            draggable: false,
            buttons: [
                {
                    text: "Cerrar",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    },
    errorDialogMsg: function (errorMessage) {
        var div = "<div>" + errorMessage + "<br></div>";
        $(div).dialog({
            title: "Advertencia",
            modal: true,
            draggable: false,
            buttons: [
                {
                    text: "Cerrar",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    },
    dialogOk: function (message) {
        var div = "<div>" + message + "<br></div>";
        $(div).dialog({
            title: "Ok",
            modal: true,
            draggable: false,
            buttons: [
                {
                    text: "Cerrar",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    },
    warningDialog: function (warningMessage) {
        var div = "<div id='warningDialog'>" + warningMessage + "<br></div>";
        $(div).dialog({
            title: "Mensaje",
            modal: true,
            draggable: false,
            buttons: [
                {
                    text: "Cerrar",
                    click: function () {
                        $(this).dialog("close");
                    }
                }
            ]
        });
    }
}