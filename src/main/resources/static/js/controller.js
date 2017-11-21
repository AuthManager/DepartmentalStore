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
    initialCart();

    function initialCart() {
        allProducts($http, $scope);
        $scope.cart = {
            items: [{product: null, sell_quantity: 0, total_price: 0}],
            cart_total_price: 0
        };
    }

    $scope.calculateUnitTotalPrice = function (index) {
        var item = $scope.cart.items[index];
        item.total_price = 0;
        if (item.product !== null && item.sell_quantity !== undefined) {
            item.total_price = item.product.price * item.sell_quantity;
        }
        $scope.calculateCartTotalPrice();
    };

    $scope.addRow = function () {
        var item = {product: null, sell_quantity: 0, total_price: 0};
        var index = $scope.cart.items.length + 1;
        $scope.cart.items.splice(index + 1, 0, item);
        $scope.calculateCartTotalPrice();
    };

    $scope.deleteRow = function ($event, item) {
        var index = $scope.cart.items.indexOf(item);
        if ($event.which === 1)
            $scope.cart.items.splice(item, 1);
        $scope.calculateCartTotalPrice();
    };

    $scope.calculateCartTotalPrice = function () {
        $scope.cart.cart_total_price = 0;
        for(var index in $scope.cart.items){
            var item = $scope.cart.items[index];
            if (item.product !== null && item.sell_quantity !== undefined) {
                $scope.cart.cart_total_price  += (item.product.price * item.sell_quantity);
            }
        }
    };

    $scope.purchase = function () {
        var saleRequest = [];
        for (var index in $scope.cart.items) {
            var item = $scope.cart.items[index];
            if (item.product !== null && item.sell_quantity !== undefined) {
                saleRequest.push({"productId": item.product.id, "sellQuantity": item.sell_quantity});
            }
        }
        $http({
            method: "POST",
            url: "api/sale",
            data : saleRequest
        }).then(
            function successCallback(response) {
                $scope.saleResponse = response.data;
                $scope.successMessage = "Successfully created purchase order with Transaction Id : " + $scope.saleResponse.transactionId;
                initialCart();
            },
            function errorCallback(response) {
                $scope.errorMessage = 'Problem occurred while searching';
            }
        );
    }

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
    allProducts($http, $scope);
    $("#start").datepicker();
    $("#end").datepicker();

    $scope.sales = function () {
        $scope.start = $('#start').val();
        $scope.end = $('#end').val();
        $scope.errorMessage = null;
        if ($scope.start && $scope.end) {
            $http({
                method: "GET",
                url: "api/sale?startDate=" + $scope.start + "&endDate=" + $scope.end,
            }).then(
                function successCallback(response) {
                    $scope.results = response.data;
                },
                function errorCallback(response) {
                    $scope.errorMessage = 'Problem occurred while updating a product.';
                }
            );
        } else {
            $scope.errorMessage = 'Please input atleast one field to update.';
        }

        function clearFields() {
            $scope.start = "";
            $scope.end = "";
            $('#start').val("");
            $('#end').val("");
        }
    };
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
                url: "api/product/" + $scope.selectedProduct.id,
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