/**
 * Created by pivotal on 9/29/15.
 */


angular.module('admin', []).controller('ad1', function($scope, $http) {

    $http.get('resource/admin/list/').success(function(data) {
        console.log("list received", data);
        $scope.listofitems= data;
    });

    $http.get("/resource/who/").success(function(data) {
        $scope.springdoUserName = data["name"];
    });


});