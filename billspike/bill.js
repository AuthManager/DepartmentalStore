var app = angular.module("app", []);
app.controller("MyCtrl", function ($scope) {

    $scope.all = [
        {
            "id": "1234",
            "name": "Cooker",
            "price": 1350,
            "quantity": 12
        },
        {
            "id": "1235",
            "name": "Rice Cooker",
            "price": 14050,
            "quantity": 10
        }
    ];

    $scope.data = {
        billproducts: [{product: null, sell_quantity: 0, total_price: 0}]
    };
    $scope.calculateUnitTotalPrice = function (index) {
        var item = $scope.data.billproducts[index];
        item.total_price = 0;
        if (item.product !== null && item.sell_quantity !== undefined) {
            item.total_price = item.product.price * item.sell_quantity;
        }
    };

    $scope.addRow = function () {
        var product = {product: null, sell_quantity: 0, total_price: 0};
        var index = $scope.data.billproducts.length + 1;
        $scope.data.billproducts.splice(index + 1, 0, product);
    };

    $scope.deleteRow = function ($event, product) {
        var index = $scope.data.billproducts.indexOf(product);
        if ($event.which == 1)
            $scope.data.billproducts.splice(index, 1);
    }

});
