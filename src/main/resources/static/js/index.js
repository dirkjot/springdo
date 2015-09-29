angular.module('index', []).controller('home', function($scope, $http) {

	$http.get('resource/list/').then(function(success) {
        data = success.data;
        console.log("/list", data);
		$scope.listofitems = data; });

	$http.get("resource/who/").then(function(success) {
        data = success.data;
        $scope.springdoUserName = data["name"]; });

	$scope.onDoneClick = function (item) {
			$http.post('/resource/done/' + item.id + '/' + item.done + '/');
		};

	// hiding showing content
	$scope.content = [];

	$scope.toggleContent = function(itemid) {
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

	$scope.savebutton = function (item) {
        $http.post('/resource/save/' + item.id + '/' + item.titleField + '/' + item.contentField + '/' + item.done + '/')
            .then(
                function(success){
                    item.brandnew = false;
                    item.title = item.titleField;
                    item.content = item.contentField;
                    $scope.editorEnabled = false;},
                function(failure){
                    console.log("save error", failure);
                });
	};

    $scope.gocancel = function(item) {
        console.log("canceled existing item");
        item.titleField = item.title;
        item.contentField = item.content;
        $scope.editorEnabled = false;
    };

    $scope.cancelbutton = function (item) {
        if (item.brandnew) {
            $scope.godelete(item);
        } else {
            $scope.gocancel(item);
        }
    };



    $scope.godelete = function (item) {
        $http.post('/resource/delete/' + item.id + '/')
        // in the future we should get an update from the server, now we just
        // remove the item ourselves
            .then(function(success) {
                listofitems = $scope.listofitems;
                for (i=listofitems.length-1; i>=0; i--) {
                    if (listofitems[i].id == item.id) {
                        listofitems.splice(i, 1);
                    }}},
                function(failure) {
                    console.log("Delete failed", failure);
                });
    };

	$scope.plusbutton = function () {
		$http.get('resource/create/').then(function(success) {
            newitem = success.data;
            newitem.brandnew = true;
			$scope.listofitems.push(newitem);
			$scope.toggleContent(newitem.id);
			$scope.goedit(newitem);
		});
	};
});
