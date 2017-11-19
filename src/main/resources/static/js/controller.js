app.controller('loginController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = false;
    isSessionActive($cookies);
    var self = this;
    self.successMessage = '';
    self.errorMessage = '';
    self.done = false;

    $scope.loginUser = function () {
        $http({
            method: "POST",
            url: "api/auth/password?no-cache=" + new Date(),
            data: {username: $scope.username, password: $scope.password}
        }).then(function successCallback(response) {
            $cookies.put("AUTHORIZATION", response.data.authcode);
            $cookies.put("FIRST_NAME", response.data.firstname);
            clearFields();
            $window.location.href = "#!/";
        }, function errorCallback(response) {
            $scope.errorMessage = 'Wrong password.Try again';
            clearFields();
        });
    };

    function isSessionActive($cookies) {
        $.ajax({
            url: "api/auth/session?ts=" + new Date(),
            type: 'GET',
            async: false,
            cache: false,
            timeout: 30000,
            success: function (data) {
                $window.location.href = "#!/";
            },
            error: function (data) {
            }
        });
    }

    function clearFields() {
        $scope.username = "";
        $scope.password = "";
        $scope.auth.$setPristine();
    }

});

app.controller('logoutController', function ($scope, $http, $cookies, $window) {
    $scope.logout = function () {
        $http({
            method: "POST",
            url: "api/auth/logout"
        }).then(function successCallback(response) {
            $cookies.remove("AUTHORIZATION");
            $cookies.remove("FIRST_NAME");
            $window.location.href = "#!/login";
        }, function errorCallback(response) {
            $scope.errorMessage = 'Unknown error occurred. Reason: ';
        });
    };

});

app.controller('dashboardController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "dashboard";
    setSession($scope, $cookies, $window);

});

app.controller('generatebillController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "generatebill";
    setSession($scope, $cookies, $window);
    allProducts($http, $scope);
});


app.controller('viewproductsController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "viewproducts";
    setSession($scope, $cookies, $window);
    allProducts($http, $scope);
});

app.controller('viewsalesController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "viewsales";
    setSession($scope, $cookies, $window);
});

app.controller('updatepriceController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "updateprice";
    setSession($scope, $cookies, $window);
    allProducts($http, $scope);

    $scope.productSelection = function (product) {
        $scope.current_price = product.price;
    };

    $scope.updatePrice = function () {
        $scope.successMessage = null;
        $scope.errorMessage = null;
        if ($scope.selectedProduct && $scope.newprice) {
            $http({
                method: "PUT",
                url: "api/product/"+ $scope.selectedProduct.name,
                data: {
                    price: $scope.newprice
                }
            }).then(
                function successCallback() {
                    $scope.successMessage = "Successfully update price for the Product";
                    allProducts($http, $scope);
                    clearFields();
                },
                function errorCallback(response) {
                    $scope.errorMessage = 'Problem occurred while updating a product.';
                }
            );
        } else {
            $scope.errorMessage = 'Please input atleast one field to update.';
        }

        function clearFields() {
            $scope.selectedProduct = "";
            $scope.newprice = "";
            $scope.current_price = "";
            $scope.updateprice.$setPristine();
        }
    };

});

app.controller('addnewitemController', function ($scope, $http, $cookies, $window) {
    $scope.show_menu = true;
    $scope.active_page = "addnewitem";
    setSession($scope, $cookies, $window);
    $scope.addProduct = function () {
        $scope.successMessage = null;
        $scope.errorMessage = null;
        if ($scope.name && $scope.price && $scope.quantity) {
            $http({
                method: "POST",
                url: "api/product",
                data: {
                    name: $scope.name,
                    price: $scope.price,
                    quantity: $scope.quantity
                }
            }).then(
                function successCallback() {
                    $scope.successMessage = "Successfully created a product";
                    clearFields();
                },
                function errorCallback(response) {
                    $scope.errorMessage = 'Problem occurred while creating a product.';
                }
            );
        } else {
            $scope.errorMessage = 'Please input atleast one field to update.';
        }

        function clearFields() {
            $scope.name = "";
            $scope.price = "";
            $scope.quantity = "";
            $scope.addproduct.$setPristine();
        }
    };
});

function deleteRow(row) {
    var billTable = document.getElementById('bill-table');
    console.log(billTable.rows.length);
    if (billTable.rows.length > 2) {
        var index = row.parentNode.parentNode.rowIndex;
        billTable.deleteRow(index);
    } else {
        alert("At least one row needs to be present");
    }
}

function insRow() {
    var billTable = document.getElementById('bill-table');
    var newRow = billTable.rows[1].cloneNode(true);
    billTable.getElementsByTagName("tbody")[0].appendChild(newRow);
}

function setSession($scope, $cookies, $window) {
    $scope.first_name = $cookies.get("FIRST_NAME");

    $.ajax({
        url: "api/auth/session?ts=" + new Date(),
        type: 'GET',
        async: false,
        cache: false,
        timeout: 30000,
        success: function (data) {
        },
        error: function (data) {
            $cookies.remove("AUTHORISATION");
            $cookies.remove("FIRST_NAME");
            $window.location.href = "#!/login"
        }
    });
}

function allProducts($http, $scope) {
    $scope.all = null;
    $scope.errorMessage = null;
    $http({
        method: "GET",
        url: "api/product/all"
    }).then(
        function successCallback(response) {
            $scope.all = response.data;
        },
        function errorCallback(response) {
            $scope.errorMessage = 'Problem occurred while searching';
        }
    );
}