app.controller('loginController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = false;
});

app.controller('dashboardController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "dashboard";
});

app.controller('generatebillController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "generatebill";
});


app.controller('viewproductsController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "viewproducts";
});

app.controller('viewsalesController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "viewsales";
});

app.controller('updatepriceController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "updateprice";
});

app.controller('addnewitemController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "addnewitem";
});

function deleteRow(row)
{
    var billTable = document.getElementById('bill-table');
    console.log(billTable.rows.length);
    if(billTable.rows.length > 2){
        var index = row.parentNode.parentNode.rowIndex;
        billTable.deleteRow(index);
    } else {
        alert("Atleast one row needs to be present");
    }
}

function insRow()
{
    var billTable = document.getElementById('bill-table');
    var newRow = billTable.rows[1].cloneNode(true);
    billTable.getElementsByTagName("tbody")[0].appendChild(newRow);
}