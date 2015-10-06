angular.module('index', []).
    controller('home', function($scope, $http) {
        $http.get('resource/list/').then(function(success) {
            $scope.listofitems = success.data;
        });
        $scope.onDoneClick = function (item) {
            console.log(item.done);
            $http.post('/resource/done/' + item.id + '/' + item.done + '/');
        };
    });
