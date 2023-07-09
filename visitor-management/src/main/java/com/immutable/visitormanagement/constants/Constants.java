package com.immutable.visitormanagement.constants;

public class Constants {

    public static final String ADMIN_EMAIL = "cr7.cherrie@gmail.com";
    public static final String SECRET_KEY_VISITOR = "visitor-ledger-mind-sprint!";


    //ALL VISITOR'S URL
    public static final String CREATE_URL_VISITOR = "/api/visitor-details/create";
    public static final String GET_ALL_URL_VISITOR = "/api/visitor-details/getAllVisitors";
    public static final String GET_BY_ID_URL_VISITOR = "/api/visitor-details/getVisitorById";
    public static final String CHECKOUT_URL_VISITOR = "/api/visitor-details/updateOutTime";
    public static final String DASHBOARD_URL_VISITOR = "/api/visitor-details/dashboardData";
    public static final String GET_VISITOR_ORGANIZATION = "/api/visitor-details/getVisitorOrganization";

    public static final String DOWNLOAD_REPORTS = "/api/visitor-details/downloadReports";


    //ALL EMPLOYEE'S URL
    public static final String CREATE_URL_EMPLOYEE = "/api/employee/create";
    public static final String GET_ALL_URL_EMPLOYEE = "/api/employee/getAllEmployees";
    public static final String GET_BY_ID_URL_EMPLOYEE = "/api/employee/getById";
    public static final String GET_BY_ID_FOR_VISITOR_EMPLOYEE = "/api/employee/getAllEmployeesForVisitor/{secretKey}";
    public static final String UPDATE_URL_EMPLOYEE = "/api/employee/update";
    public static final String DELETE_URL_EMPLOYEE = "/api/employee/delete";

    //ALL USER'S URL
    public static final String CREATE_URL_USER = "/api/user/register";
    public static final String ACTIVATE_ACCOUNT_URL_USER = "/api/user/activateAccount/{token}";
    public static final String FORGOT_PASSWORD_URL_USER = "/api/user/forgot-password/{email}";
    public static final String RESET_PASSWORD_URL_USER = "/api/user/reset-password/{token}/{password}";
    public static final String SEND_EMAIL_URL_USER = "/api/user/sendReports/{filePath}";


    //ALL AUTHENTICATION CONTROLLER
    public static final String MAIN_URL_LOGIN = "/api/auth/login";


}
