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
		item.titleField = item.title;
		item.contentField = item.content;
		$scope.currentEditItem = item.id;
	};

	$scope.saveedit = function (item) {
		item.title = item.titleField;
		item.content = item.contentField;
		$scope.editorEnabled = false;
		// TODO post this stuff
		// post('/resource/save/<id>/title/content/done/')
		// post('/resource/new/title/content/done/')

	};



});
