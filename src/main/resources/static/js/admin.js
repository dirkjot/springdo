/**
 * Created by pivotal on 9/29/15.
 */


angular.module('admin', []).controller('ad1', function($scope, $http) {

    $http.get('resource/admin/list/').then(function(success) {
        listofitems = success.data;
        console.log("list received", listofitems);
        $scope.listofitems= listofitems;
    });

    $http.get("/resource/who/").then(function(success) {
        data = success.data;
        $scope.springdoUserName = data["name"];
    });


});