angular.module('hello', []).controller('home', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.greeting = data;
		//$scope.greeting = {id: 'xxx', content: 'Hello World!'};
	})
});
