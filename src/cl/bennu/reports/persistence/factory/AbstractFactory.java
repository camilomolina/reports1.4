package cl.bennu.reports.persistence.factory;

import cl.bennu.reports.persistence.dao.*;
import cl.bennu.reports.persistence.impl.ibatis.*;


public class AbstractFactory {

    public static IAreaDAO getAreaDAO() {
        return new AreaImpl();
    }

    public static IControllerDAO getControllerDAO() {
        return new ControllerImpl();
    }

    public static IParameterDAO getParameterDAO() {
        return new ParameterImpl();
    }

    public static IParameterTypeDAO getParameterTypeDAO() {
        return new ParameterTypeImpl();
    }

    public static IConexionDAO getConexionDAO() {
        return new ConexionImpl();
    }

    public static IReportDAO getReportDAO() {
        return new ReportImpl();
    }

    public static ILogDAO getLogDAO() {
        return new LogImpl();
    }
}





