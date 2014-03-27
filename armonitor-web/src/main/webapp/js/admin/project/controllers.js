controllers.controller('ProjectAdminCtrl', ['$scope','$routeParams','ProjectAdminService',
	function($scope, $routeParams, ProjectAdminService) {

	if ($routeParams.guid) {
		ProjectAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	} else {
		ProjectAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		ProjectAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.remove = function() {
		ProjectAdminService.remove({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};

}]);

controllers.controller('ProjectAppAdminCtrl', ['$scope','$routeParams','ProjectAdminService',
	function($scope, $routeParams, ProjectAdminService) {
	var _load = true;
	
	function _search() {
		ProjectAdminService.app({guid: $routeParams.guid}, function(response) {
			$scope.all = response;
		});
	};
	
	$scope.load = function() {
		if (_load) {
			_search();
		}
		_load = false;
	};
	
	$scope.reload = function() {
		$scope.filter = null;
		_search();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 || (row.type !== null && row.type.indexOf(tmp) !== -1)));
		}
		return true;
	};
	
}]);

controllers.controller('ProjectBtsAdminCtrl', ['$scope','$routeParams','BTSAdminService','ProjectAdminService',
	function($scope, $routeParams, BTSAdminService, ProjectAdminService) {

	$scope.bts = {selected: null};
	var _load = true;

	$scope.load = function() {

		if (_load) {
			BTSAdminService.list({}, function(response) {
				$scope.btslist = response;
			});
			ProjectAdminService.bts({guid: $routeParams.guid}, function(response) {
				$scope.bts.selected = response.guid;
			});
		}
		_load = false;
	};

	$scope.addbts = function() {
		ProjectAdminService.addbts({guid: $routeParams.guid, id: $scope.bts.selected}, function(response) {

		});
	};
}]);

controllers.controller('ProjectSearchAdminCtrl', ['$scope','ProjectAdminService',
	function($scope, ProjectAdminService) {

	function _startup() {
		ProjectAdminService.all({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();

	$scope.reload = function() {
		$scope.filter = null;
		_startup();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 || (row.btsId !== null && row.btsId.indexOf(tmp) !== -1)));
		}
		return true;
	};
}]);