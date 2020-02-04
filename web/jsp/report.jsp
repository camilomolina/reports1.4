<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>Color Admin | Form Elements</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />

	<!-- ================== BEGIN BASE CSS STYLE ================== -->
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
	<link href="/reports14/assets/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />
	<link href="/reports14/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="/reports14/assets/plugins/font-awesome/css/all.min.css" rel="stylesheet" />
	<link href="/reports14/assets/plugins/animate/animate.min.css" rel="stylesheet" />
	<link href="/reports14/assets/css/default/style.min.css" rel="stylesheet" />
	<link href="/reports14/assets/css/default/style-responsive.min.css" rel="stylesheet" />
	<link href="/reports14/assets/css/default/theme/default.css" rel="stylesheet" id="theme" />
	<!-- ================== END BASE CSS STYLE ================== -->

	<!-- ================== BEGIN BASE JS ================== -->
	<script src="/reports14/assets/plugins/pace/pace.min.js"></script>
	<!-- ================== END BASE JS ================== -->
</head>
<body>

<div id="dialogBase64" style="display: none;">
    Generando reporte ...
    <br/>
    <br/>
    <center><img src='/reports14/img/loading.gif'/></center>
    <br>
</div>

    <html:form action="/report" styleId="reportForm">
        <html:hidden property="method" styleId="method"/>
        <html:hidden property="id" styleId="id"/>
        <html:hidden property="report" styleId="report"/>

        <div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
            <div id="content" class="content">
                <h1 class="page-header"><bean:write name="reportForm" property="reportDTO.name"/> <small><bean:write name="reportForm" property="reportDTO.description"/></small></h1>
                <div class="row">

                    <div class="col-lg-10">
                        <div class="panel panel-inverse" data-sortable-id="form-stuff-1">

                            <div class="panel-body">
                                <div class="alert alert-secondary">
                                    ...
                                </div>
                                <div class="form-group row m-b-15">
                                    <logic:notEmpty name="reportForm"  property="reportDTO.parameterList">
                                        <logic:iterate id="parameter" name="reportForm" property="reportDTO.parameterList">

                                            <label class="col-form-label col-md-3"><bean:write name="parameter" property="name"/></label>
                                            <div class="col-md-9">
                                                <logic:equal value="1" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Alfanumerico-->
                                                    <input type="text"
                                                        class="form-control m-b-5"
                                                        placeholder="Enter ..."
                                                        name="parameter_<bean:write name="parameter" property="id"/>"
                                                        id="parameter_<bean:write name="parameter" property="id"/>_id"
                                                        parameterRequired="<bean:write name="parameter" property="required" />"
                                                        parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                        parameterName="<bean:write name="parameter" property="name" />"
                                                        parameterData1="<bean:write name="parameter" property="data1" />"
                                                        parameterData2="<bean:write name="parameter" property="data2" />"
                                                    />
                                                </logic:equal>
                                                <logic:equal value="2" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Fecha-->
                                                    <input type="text"
                                                        class="form-control"
                                                        placeholder="Select Date"

                                                        size="8"
                                                        name="parameter_<bean:write name="parameter" property="id"/>"
                                                        id="parameter_<bean:write name="parameter" property="id"/>_id"
                                                        parameterRequired="<bean:write name="parameter" property="required" />"
                                                        parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                        parameterName="<bean:write name="parameter" property="name" />"
                                                        parameterData1="<bean:write name="parameter" property="data1" />"
                                                        parameterData2="<bean:write name="parameter" property="data2" />"
                                                    />

                                                    <logic:equal name="parameter" property="data2" value="1" >(Dia/Mes/A&ntilde;o)</logic:equal>
                                                    <logic:equal name="parameter" property="data2" value="2" >(Mes/A&ntilde;o)</logic:equal>
                                                    <logic:equal name="parameter" property="data2" value="3" >(A&ntilde;o)</logic:equal>
                                                </logic:equal>
                                                <logic:equal value="3" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Numerico-->
                                                    <input type="text"
                                                        class="form-control m-b-5"
                                                        placeholder="Enter ..."

                                                        size="15"
                                                        name="parameter_<bean:write name="parameter" property="id"/>"
                                                        id="parameter_<bean:write name="parameter" property="id"/>_id"
                                                        parameterRequired="<bean:write name="parameter" property="required" />"
                                                        parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                        parameterName="<bean:write name="parameter" property="name" />"
                                                        parameterData1="<bean:write name="parameter" property="data1" />"
                                                        parameterData2="<bean:write name="parameter" property="data2" />"
                                                    />
                                                </logic:equal>
                                                <logic:equal value="4" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Booleano-->

                                                    <input class="form-input" type="checkbox" value=""

                                                        name="parameter_<bean:write name="parameter" property="id"/>"
                                                        id="parameter_<bean:write name="parameter" property="id"/>_id"
                                                        parameterRequired="<bean:write name="parameter" property="required" />"
                                                        parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                        parameterName="<bean:write name="parameter" property="name" />"
                                                        parameterData1="<bean:write name="parameter" property="data1" />"
                                                        parameterData2="<bean:write name="parameter" property="data2" />"
                                                    >
                                                </logic:equal>
                                                <logic:equal value="5" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Rango Fechas-->
                                                    <div class="input-group input-daterange">
                                                        <input type="text"
                                                            class="form-control"
                                                            placeholder="Date Start"

                                                            size="8"
                                                            name="parameter_<bean:write name="parameter" property="id"/>_init"
                                                            id="parameter_<bean:write name="parameter" property="id"/>_id_init"
                                                            parameterRequired="<bean:write name="parameter" property="required" />"
                                                            parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                            parameterName="<bean:write name="parameter" property="name" />"
                                                            parameterData1="<bean:write name="parameter" property="data1" />"
                                                            parameterData2="<bean:write name="parameter" property="data2" />"
                                                        />
                                                        <span class="input-group-addon">to</span>
                                                        <input type="text"
                                                            class="form-control"
                                                            placeholder="Date End"

                                                            size="8"
                                                            name="parameter_<bean:write name="parameter" property="id"/>_final"
                                                            id="parameter_<bean:write name="parameter" property="id"/>_id_final"
                                                            parameterRequired="<bean:write name="parameter" property="required" />"
                                                            parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                            parameterName="<bean:write name="parameter" property="name" />"
                                                            parameterData1="<bean:write name="parameter" property="data1" />"
                                                            parameterData2="<bean:write name="parameter" property="data2" />"
                                                        />
                                                    </div>

                                                    <logic:equal name="parameter" property="data2" value="1" >(Dia/Mes/A&ntilde;o)</logic:equal>
                                                    <logic:equal name="parameter" property="data2" value="2" >(Mes/A&ntilde;o)</logic:equal>
                                                    <logic:equal name="parameter" property="data2" value="3" >(A&ntilde;o)</logic:equal>

                                                    <small class="f-s-12 text-grey-darker"> </small>
                                                </logic:equal>
                                                <logic:equal value="6" name="parameter" property="parameterTypeEnum.id">
                                                    <!--Alfanumerico-->
                                                    <input type="text"
                                                        class="form-control m-b-5"
                                                        placeholder="Enter ..."

                                                        size="20"
                                                        name="parameter_<bean:write name="parameter" property="id"/>"
                                                        id="parameter_<bean:write name="parameter" property="id"/>_id"
                                                        parameterRequired="<bean:write name="parameter" property="required" />"
                                                        parameterType="<bean:write name="parameter" property="parameterTypeEnum.id" />"
                                                        parameterName="<bean:write name="parameter" property="name" />"
                                                        parameterData1="<bean:write name="parameter" property="data1" />"
                                                        parameterData2="<bean:write name="parameter" property="data2" />"
                                                    />
                                                </logic:equal>
                                                <small class="f-s-12 text-grey-darker"> </small>
                                            </div>

                                        </logic:iterate>
                                    </logic:notEmpty>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="form-group row m-b-15">
                                    <button type="button" class="btn btn-green m-r-5 m-b-5" onclick="Report.generate();">Generar Informe</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </html:form>

<fieldset style="display:none" id="loader">
    <span>Espere un Momento</span>
    <img src="/reports14/img/loading.gif"/>
</fieldset>

	<!-- ================== BEGIN BASE JS ================== -->
	<script src="/reports14/assets/plugins/jquery/jquery-3.3.1.min.js"></script>
	<script src="/reports14/assets/plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="/reports14/assets/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!--[if lt IE 9]>
		<script src="/reports14/assets/crossbrowserjs/html5shiv.js"></script>
		<script src="/reports14/assets/crossbrowserjs/respond.min.js"></script>
		<script src="/reports14/assets/crossbrowserjs/excanvas.min.js"></script>
	<![endif]-->
	<script src="/reports14/assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="/reports14/assets/plugins/js-cookie/js.cookie.js"></script>
	<script src="/reports14/assets/js/theme/default.min.js"></script>
	<script src="/reports14/assets/js/apps.min.js"></script>
	<!-- ================== END BASE JS ================== -->

	<!-- ================== BEGIN PAGE LEVEL JS ================== -->
	<script src="/reports14/assets/plugins/highlight/highlight.common.js"></script>
	<script src="/reports14/assets/js/demo/render.highlight.js"></script>
		<!-- ================== END PAGE LEVEL JS ================== -->
	<script>
		$(document).ready(function() {
			App.init({
				disableDraggablePanel: true
			});
			Highlight.init();
		});
	</script>
    <script type="text/javascript" src="/reports14/js/generic.js"></script>
	<script type="text/javascript" src="/reports14/js/report.js"></script>

</body>
</html>
