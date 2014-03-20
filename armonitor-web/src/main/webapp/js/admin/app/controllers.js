
controllers.controller('AppSysAdminCtrl', function($scope, $routeParams, AppAdminService) {
	var _load = true;
	
	function _search() {
		AppAdminService.sys({guid: $routeParams.guid}, function(response) {
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
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 
					|| (row.clazz !== null && row.clazz.indexOf(tmp) !== -1)
					|| (row.domain !== null && row.domain.indexOf(tmp) !== -1)
					|| (row.service !== null && row.service.indexOf(tmp) !== -1)));
		}
		return true;
	};
	
});

controllers.controller('AppProjectAdminCtrl', function($scope, $routeParams, ProjectAdminService, AppAdminService) {

	$scope.item = {selected: null};
	var _load = true;

	$scope.load = function() {

		if (_load) {
			ProjectAdminService.list({}, function(response) {
				$scope.list = response;
			});
			AppAdminService.project({guid: $routeParams.guid}, function(response) {
				$scope.item.selected = response.guid;
			});
		}
		_load = false;
	};

	$scope.add = function() {
		AppAdminService.addproject({guid: $routeParams.guid, id: $scope.item.selected}, function(response) {

		});
	};
});

controllers.controller('AppScmAdminCtrl', function($scope, $routeParams, SCMAdminService, AppAdminService) {

	$scope.bts = {selected: null};
	var _load = true;

	$scope.load = function() {

		if (_load) {
			SCMAdminService.list({}, function(response) {
				$scope.btslist = response;
			});
			AppAdminService.scm({guid: $routeParams.guid}, function(response) {
				$scope.bts.selected = response.guid;
			});
		}
		_load = false;
	};

	$scope.addscm = function() {
		AppAdminService.addscm({guid: $routeParams.guid, id: $scope.bts.selected}, function(response) {

		});
	};
});

controllers.controller('AppAdminCtrl', function($scope, $routeParams, AppAdminService) {

	if ($routeParams.guid) {
		AppAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	} else {
		AppAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		AppAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.delete = function() {
		AppAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};

});

controllers.controller('AppSearchAdminCtrl', function($scope, AppAdminService) {

	function _startup() {
		AppAdminService.all({}, function(response) {
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
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 || (row.type !== null && row.type.indexOf(tmp) !== -1)));
		}
		return true;
	};
});