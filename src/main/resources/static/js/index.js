angular.module('index', []).controller('home', function($scope, $http) {
	$http.get('resource/list/').success(function(data) {
		//$scope.greeting = data;
		//$scope.greeting = {id: 'xxx', content: 'Hello World!'};
		$scope.listofitems = data;
		$scope.onDoneClick = function (item) {
			console.log(item.done);
			$http.post('/resource/done/' + item.id + '/' + item.done + '/');
		}
	})
});
