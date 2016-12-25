var app = angular.module('receiptsapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/list.html',
        controller: 'ListCtrl'
    }).when('/create', {
        templateUrl: 'views/create.html',
        controller: 'CreateCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('ListCtrl', function ($scope, $http) {
    $http.get('/api/v1/prescriptions').success(function (data) {
        $scope.receipts = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

});

app.controller('CreateCtrl', function ($scope, $http, $location) {
    $scope.receipts = {
        done: false
    };

    $scope.createReceipt = function () {
        console.log($scope.receipts);
        $http.post('/api/v1/prescriptions', $scope.receipts).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});