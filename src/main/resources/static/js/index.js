angular.module('index', []).controller('home', function($scope, $http) {

	$http.get('resource/list/').success(function(data) {
		$scope.listofitems = data; });

	$scope.onDoneClick = function (item) {
			$http.post('/resource/done/' + item.id + '/' + item.done + '/')
				.then(function (success) { console.log("backend received done state change", success) },
				      function (failure) { console.log("backend error", failure) });
		};

	$scope.content = [];
	$scope.toggleContent = function(itemid) {
		$scope.content[itemid] = !$scope.content[itemid];
	};
});
