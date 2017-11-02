var app = angular.module('app', ['ngCookies', 'ngRoute', 'ngResource']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/pages/login.html',
            controller: 'loginController'
        })
        .when('/login', {
            templateUrl: 'views/pages/login.html',
            controller: 'loginController'
        })
        .when('/dashboard', {
            templateUrl: 'views/pages/dashboard.html',
            controller: 'dashboardController'
        })
        .when('/generatebill', {
            templateUrl: 'views/pages/generatebill.html',
            controller: 'generatebillController'
        })
        .when('/viewproducts', {
            templateUrl: 'views/pages/viewproducts.html',
            controller: 'viewproductsController'
        })
        .when('/updateprice', {
            templateUrl: 'views/pages/updateprice.html',
            controller: 'updatepriceController'
        })
        .when('/viewsales', {
            templateUrl: 'views/pages/viewsales.html',
            controller: 'viewsalesController'
        })
        .when('/addnewitem', {
            templateUrl: 'views/pages/addnewitem.html',
            controller: 'addnewitemController'
        })
        .otherwise(
            {redirectTo: '/'}
        );
}]);