// mueve elementos entre combobox
function moveSelect(cboQuita, cboAgrega) {
	var objCboAsociados = cboAgrega;
	var objCbo = cboQuita;
	var length = objCbo.options.length;
	var maxLength = objCboAsociados.options.length;

	for( var i = 0; i < length; i++ ) {
		if ( objCbo.options[i] != null && ( objCbo.options[i].selected ) ) {

			objCboAsociados.options.add(new Option( objCbo.options[i].text, objCbo.options[i].value, objCbo.options[i].defaultSelected, objCbo.options[i].selected));
		}
	}

	//	Eliminacion de Options del Select
	for( var j = length - 1; j >= 0; j-- ) {
		if ( objCbo.options[j] != null && ( objCbo.options[j].selected ) ) {
			objCbo.options[j] = null;
		}
	}
}

function cleanSelect(cbo) {
    if (cbo == null) return;
    
    for( var i = cbo.length - 1; i >= 0; i-- ) cbo.options[i] = null;
}