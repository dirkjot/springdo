angular.module('index', []).controller('home', function($scope, $http) {

	$http.get('resource/list/').success(function(data) {
		//$scope.greeting = data;
		//$scope.greeting = {id: 'xxx', content: 'Hello World!'};
		$scope.listofitems = data; });

	$scope.onDoneClick = function (item) {
			console.log(item.done);
			$http.post('/resource/done/' + item.id + '/' + item.done + '/');};

	// hiding showing content
	$scope.content = [];
	$scope.toggleContent = function(myvar) {
		//console.log($scope.content[myvar]);
		for (var key in $scope.content) {
			if (key != myvar) {
			$scope.content[key] = false; }}
		$scope.content[myvar] = !$scope.content[myvar]; };


	// editing content
	$scope.editorEnabled = false;

	$scope.goedit = function (item) {

		$scope.editorEnabled = true;
		$scope.editableTitle = $scope.item.title;
		$scope.editableContent = $scope.item.content;
	};

	$scope.saveedit = function (item) {
		$scope.item.title = $scope.editableTitle;
		$scope.item.content = $scope.editableContent;
		$scope.editorEnabled = false;
		// TODO post this stuff
	};


});
