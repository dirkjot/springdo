angular.module('index', []).controller('home', function($scope, $http) {

	$http.get('resource/list/').success(function(data) {
		//$scope.greeting = data;
		//$scope.greeting = {id: 'xxx', content: 'Hello World!'};
		$scope.listofitems = data; });

	$scope.onDoneClick = function (item) {
			//console.log(item.done);
			$http.post('/resource/done/' + item.id + '/' + item.done + '/');
		//console.log(item.done);
		};

	// hiding showing content
	$scope.content = [];

	$scope.toggleContent = function(itemid) {
		//console.log($scope.content[itemid]);
		$scope.editorEnabled = false;
		for (var key in $scope.content) {
			if (key != itemid) {
				$scope.content[key] = false; }}
		$scope.content[itemid] = !$scope.content[itemid]; };


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
		$http.post('/resource/save/' + item.id + '/' + item.title + '/' + item.content + '/' + item.done + '/');

	};

	$scope.plusbutton = function () {
		$scope.listofitems.push({"id": 6, "title": "new item", "content": "Hi", "done": "no"});
	};



});
