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
	$scope.contentField = [];
	$scope.titleField = [];

	$scope.toggleContent = function(myvar) {
		//console.log($scope.content[myvar]);
		$scope.editorEnabled = false;
		for (var key in $scope.content) {
			if (key != myvar) {
				$scope.content[key] = false; }}
		$scope.content[myvar] = !$scope.content[myvar]; };


	// editing content
	$scope.editorEnabled = false;

	$scope.goedit = function (item) {
		$scope.editorEnabled = true;
		$scope.titleField[item.id] = item.title;
		$scope.contentField[item.id] = item.content;
		$scope.currentEditItem = item.id;
	};

	$scope.saveedit = function (item) {
		//console.log([item.title, $scope.editableTitle]);
		item.title = $scope.titleField[item.id];
		item.content = $scope.contentField[item.id];
		//console.log([item.title, $scope.editableTitle]);
		$scope.editorEnabled = false;
		// TODO post this stuff
	};



});
