//	Constantes de Teclas utilizadas en los Eventos de KeyPress
var KEY_ENTER = 13;
var KEY_TAB = 9;
var KEY_LETTERS = "abcdefghijklmn�opqrstuvwxyzABCDEFGHIJKLMN�OPQRSTUVWXYZ����������";
var KEY_NUMBERS = "0123456789";
var KEY_DECIMAL_SEPARATOR = ".";
var KEY_MILES_SEPARATOR = '.';
var KEY_RUT = KEY_NUMBERS + "-kK";
var KEY_SPACE = " ";
var KEY_LETTER_SEPARATORS = "-_";
var KEY_LETTER_SPECIAL = "()[]=$%&!��?.,;:+-*/@";
var KEY_DATE_SEPARATORS = "/";
var KEY_ALFANUMERIC = KEY_LETTERS + KEY_NUMBERS + KEY_SPACE + KEY_LETTER_SEPARATORS;
var KEY_CODE = KEY_LETTERS + KEY_NUMBERS + KEY_LETTER_SEPARATORS;
var KEY_DESCRIPTION = KEY_LETTERS + KEY_NUMBERS + KEY_LETTER_SEPARATORS + KEY_SPACE + KEY_LETTER_SPECIAL;
var DEF_UPPERCASE = true;
//  Constantes de Expresiones Regulares utilizadas para validar textos como numeros, fechas, correos

var RGX_IGNORE_CASE = true;
var RGX_PTR_SEPARATOR = '^[ ]*$'
var RGX_PTR_INTEGER_NUMBER = '^\\d*[0-9]$'

var RGX_PTR_DECIMAL_NUMBER = '^\\d*[0-9](\\' + KEY_DECIMAL_SEPARATOR + '\\d*[0-9])?$';
var RGX_PTR_DATE = '^([0,1,2][0-9]|3[0,1])' + KEY_DATE_SEPARATORS + '(0[0-9]|1[0,1,2])' + KEY_DATE_SEPARATORS + '\\d{4}$';
var RGX_PTR_TIME_12 = '^([0-1][0-9]|[2][0-3])(:([0-5][0-9])){1,2}( (AM|PM))?$';
var RGX_PTR_TIME_24 = '^([0-1][0-9]|[2][0-3])(:([0-5][0-9])){1,2}$';
var RGX_FILENAMES = 'pdf|txt|doc|csv|xls|gif|jpeg|jpg|jpe|png|bmp|tiff';
var RGX_PTR_FILENAME = '^[a-zA-Z0-9 -_\\.]+\\.(' + RGX_FILENAMES + ')$';
var RGX_PTR_FULL_FILENAME = '^([a-zA-Z]:\\\\)?[a-zA-Z0-9 -_\\\\\\.]+[a-zA-Z0-9 -_\\.]+\\.(' + RGX_FILENAMES + ')$';
var RGX_PTR_EMAIL = '^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$';
var RGX_PTR_IP_ADDRESS = '^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$';
var RGX_PTR_URL = 'http[s]?://|ftp://(www\\.)?[a-zA-Z0-9-\\._/:?&=]+\\.(cl|com|org|net|mil|edu|ca|co.uk|com.au|gov|pdf|jpg|jpeg|gif|tif|tiff|png|txt|html|htm|asp|php|jsp|do|action)$';

var isNS = document.layers;
var isIE = document.all;

( function($) {

//  Funci�n que verifica que el valor ingresado sea un n�mero sin decimales
$.fn.IsValid_Integer = function(classError,classValid) {
    var isValid = false;
    var value = $(this).val();
    if (value != "") {
         $(this).parseNumber({format:"#,###", locale:"es"});
        value = parseInt($(this).val());
        if (value > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_INTEGER_NUMBER);
            $(this).formatNumber({format:"#,###", locale:"es"});
        }
    }
    if(isValid){
       $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea un n�mero con decimales
$.fn.IsValid_Decimal = function(classError,classValid) {
    var isValid = false;
    var value = $(this).val();
    if (value != "") {
        $(this).parseNumber({format:"#,###.00", locale:"es"});
        value = parseFloat($(this).val());
        if (value > 0.01) {
            isValid = EvaluateExpresion(value, RGX_PTR_DECIMAL_NUMBER);
            $(this).formatNumber({format:"#,###.00", locale:"es"});
        }
    }
    if(isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea una Fecha
$.fn.IsValid_Date = function(classError,classValid) {
    var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            if (EvaluateExpresion(value, RGX_PTR_DATE)) {
                var dtPart = value.split(KEY_DATE_SEPARATORS);
                var dt = new Date(dtPart[1] + KEY_DATE_SEPARATORS + dtPart[0] + KEY_DATE_SEPARATORS + dtPart[2]);
                isValid = (dt.getDate() == dtPart[0]) && ((dt.getMonth() + 1) == dtPart[1]) && (dt.getFullYear() == dtPart[2]);
            }
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea hora en formato 12 Hrs
$.fn.IsValid_Time_12 = function(classError,classValid) {
    var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_TIME_12);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea hora en formato 24 Hrs
$.fn.IsValid_Time_24 = function(classError,classValid){
   var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_TIME_24);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};
//  Funci�n que verifica que el valor ingresado sea un dia valido para el mes actual
$.fn.IsValid_Day = function(classError,month,classValid) {

    var isValid = false;
     var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            var day = parseInt(value);
            var i =   parseInt(month);
            var dias_por_mes = new Array(12)
            dias_por_mes[1] = 31;
            dias_por_mes[2] = 28;
            dias_por_mes[3] = 31;
            dias_por_mes[4] = 30;
            dias_por_mes[5] = 31;
            dias_por_mes[6] = 30;
            dias_por_mes[7] = 31;
            dias_por_mes[8] = 31;
            dias_por_mes[9] = 30;
            dias_por_mes[10] = 31;
            dias_por_mes[11] = 30;
            dias_por_mes[12] = 31;
            if (day >=1 &&day <= dias_por_mes[i] ){
               isValid = true;
            }
        }
    }
     if(isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};


$.fn.IsValid_Month = function(classError,classValid) {
    var isValid = false;
     var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            var month = parseInt(value);
            if(month>=1 && month<=12){
                isValid = true;
            }
        }
    }
     if(isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};

$.fn.IsValid_Year = function(classError,min,max,classValid) {
    var isValid = false;
     var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            var year = parseInt(value);
            if(year>=min && year<=max){
                isValid = true;
            }
        }
    }
    if(isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea un nombre de archivo v�lido
$.fn.IsValid_Filename = function(classError) {
     var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_FILENAME);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea un nombre de archivo v�lido con ruta completa
$.fn.IsValid_FullFilename = function(classError) {
     var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_FULL_FILENAME);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea correo electr�nico
$.fn.IsValid_Email = function(classError,classValid) {
    var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_EMAIL);
        }
    }
    if(isValid){
	   $(this).attr('class',classValid);
	 } else {
        $(this).attr('class',classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea un numero IP
$.fn.IsValid_IP_Address = function(classError) {
     var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_IP_ADDRESS);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funci�n que verifica que el valor ingresado sea una URL (Http, Https, FTP)
$.fn.IsValid_URL = function(classError) {
    var isValid = false;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_URL);
        }
    }
    if(isValid){
	   $(this).removeClass(classError);
	 } else {
        $(this).addClass(classError);

     }
    return isValid;
};

//  Funcion que verifica que el Rut Ingresado es v�lido y aplica para rut con formato
//  99.999.999-D, 99999999-D, 99.999.999D, 99999999D
$.fn.IsValid_Rut = function(classError,classValid) {
    var isValid = false;
    var text = $(this).val();
    if (text != null) {
        if (text.length > 0) {
            var strRutFull = $(this).ReplaceString(text, KEY_MILES_SEPARATOR, '');
            strRutFull = $(this).ReplaceString(strRutFull, '-', '');
            if (strRutFull.length >= 2) {
                var strDv = strRutFull.charAt(strRutFull.length - 1).toUpperCase();
                var strRutValue = strRutFull.substring(0, strRutFull.length - 1);
                var intSuma = 0;
                if (!isNaN(strRutValue)) {
                    var lngRutValue = Number(strRutValue);
                    var intLar = strRutValue.length;
                    while (intLar > 0) {
                        var intBase = parseFloat(Math.pow(10, (intLar - 1)));
                        var intMul = (( intLar - 1 ) % 6) + 2;
                        var intDM = parseInt(lngRutValue / intBase);
                        intSuma = intSuma + (intDM * intMul);
                        lngRutValue = lngRutValue - (intDM * intBase);
                        intLar--;
                    }
                    var intDv = 11 - intSuma % 11;
                    var Dv = ( intDv == 10 ? 'K' : ( intDv == 11 ? '0' : intDv + '') );
                    isValid = strDv == Dv;
                }
            }
        }
    }
    if(isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return isValid;
};

//  Funcion que verifica si el texto ingresado es valido
$.fn.IsValid_String = function(classError,classValid){
    var isValid = true;
    var value = $(this).val();
    if (value != null) {
        if (value.length > 0) {
            isValid = EvaluateExpresion(value, RGX_PTR_SEPARATOR);
        }
    }
    if(!isValid){
	   $(this).attr("class",classValid);
     } else {
      $(this).attr("class",classError);
     }
    return !isValid;
};

//  Funcion que remplaza en un Texto una expresion por otro texto
$.fn.ReplaceString = function(text, exp, newText) {
    while (text.indexOf(exp) >= 0) {
        text = text.replace(exp, newText);
    }
    return text;
};

//  Funcion que formatea un Rut, formato 9999999-D
$.fn.Format_Rut = function() {
    var rut = $(this).val();
    if ( rut != null && rut.length > 1) {
        rut = $(this).ReplaceString($(this).ReplaceString(rut, KEY_MILES_SEPARATOR, ''), '-', '');
        var strDv = rut.charAt(rut.length - 1).toUpperCase();
        var strRutValue = rut.substring(0, rut.length - 1);
        rut = strRutValue + "-" + strDv;
    }
    $(this).val(rut);
};
// Formatea un entero 1.000
$.fn.FormatInteger = function() {
     $(this).parseNumber({format:"#,###",locale:"es"});
     if(parseInt($(this).val()) >= 1){
      $(this).formatNumber({format:"#,###",locale:"es"});
     }
};

// Formatea un decimal 1.000,00  solo si es mayor a 1
$.fn.FormatDecimal = function() {
     $(this).parseNumber({format:"#,###.00", locale:"es"});
     if(parseFloat($(this).val()) >= 0.01){
      $(this).formatNumber({format:"#,###.00",locale:"es"});
     }
};

$.fn.validateSelect = function() {
     var x = false;
     if($(this).val()!= "" && $(this).val() != '0'){
     $(this).parseNumber({format:"#,###.00", locale:"es"});
     x = parseFloat($(this).val()) >= 0.01;
     $(this).formatNumber({format:"#,###.00",locale:"es"});
     }
    return x;
};

$.fn.validateNumeric = function() {
      var obj = $(this);
      var result = false;
      for (var i=0 ; i<obj.val().length ; i++) {
        var letter = obj.val().charAt(i);
        result = letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9'|| letter == ','|| letter == '.';

    }
    return result;
};

//  Funcion que evalua un texto seg�n una expresi�n regular
function EvaluateExpresion(expresion, pattern) {
    var isOk = false;
    if (expresion != null && pattern != null) {
        var options = (RGX_IGNORE_CASE) ? "i" : "";
        var regExp = new RegExp(pattern, options);
        isOk = regExp.test(expresion);
    }
    return isOk;
};




$.fn.OnlyRutCharacter = function(e) {
    if(e.which != 8 && e.which != 0){
    var letter = String.fromCharCode(e.which);
    if( !(letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == 'k'  || letter == 'K'|| letter == '-') ){
       e.preventDefault();
	 }
   }
};

$.fn.NumberIntegerOnly = function(e) {
    if(e.which != 8 && e.which != 0){
    var letter = String.fromCharCode(e.which);
    if(!(letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == '.') ){
       e.preventDefault();
	 }
   }
};

$.fn.NumberDecimalOnly = function(e) {
    if(e.which != 8 && e.which != 0){
    var letter = String.fromCharCode(e.which);
    if(!(letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == '.' || letter == ',') ){
       e.preventDefault();
	 }
   }
};

    $.fn.NumberDecimalOnly2 = function(e) {
        if(e.which != 8 && e.which != 0){
        var letter = String.fromCharCode(e.which);
        if(!(letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == ',') ){
           e.preventDefault();
         }
       }
    };

$.fn.NumberOnly = function(e) {
    if(e.which != 8 && e.which != 0){
    var letter = String.fromCharCode(e.which);
    if(!(letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9') ){
       e.preventDefault();
	 }
   }
};


$.fn.OnlyIpCharacter = function() {
    var obj = $(this);
    var result = '';
    for (var i=0 ; i<obj.val().length ; i++) {
        var letter = obj.val().charAt(i);
        if (letter == '0' || letter == '1' || letter == '2' || letter == '3' || letter == '4' || letter == '5' || letter == '6' || letter == '7' || letter == '8' || letter == '9' || letter == '.'  ) {
            result = result + letter;
        }
    }
    $(this).val(result);
};


$.fn.ValueUF = function(uf){
  if($(this).val()!=""){
  $(this).parseNumber({format:"#,###.00", locale:"es"});
  var value = (Math.round(( $(this).val() / uf)*100)/100);
  if(value < 0.01){
   $(this).val(0);
  } else {
  $(this).val(value);
  $(this).formatNumber({format:"#.##0,00",locale:"es"});
  }
 }
}

$.fn.ValueMoney = function(uf){
  if($(this).val()!=""){
  $(this).parseNumber({format:"#,###", locale:"es"});
  var value = $(this).val() * uf;
  $(this).val(value);
  $(this).formatNumber({format:"#,###.00",locale:"es"});
  }
}

})(jQuery);