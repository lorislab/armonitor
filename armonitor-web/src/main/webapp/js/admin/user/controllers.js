controllers.controller('UserRoleAdminCtrl', ['$scope','$routeParams','UserAdminService','RoleAdminService', 
	function($scope, $routeParams, UserAdminService, RoleAdminService) {
	var _load = true;

	RoleAdminService.all({}, function(response) {
		$scope.all = response;
	});
	
	function _search() {
		
		UserAdminService.roles({guid: $routeParams.guid}, function(response) {
			for (var i=0;i<$scope.all.length;i++) {	
				var _item = $scope.all[i];
				_item.remove = response[_item.guid];				
			}
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
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1));
		}
		return true;
	};
	
	$scope.addRole = function(role) {
		UserAdminService.addrole({guid: $routeParams.guid, role: role.guid}, function(response) {
			_search();
		});
	};

	$scope.removeRole = function(role) {
		UserAdminService.removerole({guid: $routeParams.guid, role: role.guid}, function(response) {
			_search();
		});		
	};	
}]);

controllers.controller('UserAdminCtrl', ['$scope','$routeParams','UserAdminService', 
	function($scope, $routeParams, UserAdminService) {

	$scope.pswd = {n: null, c: null, o: null};

	if ($routeParams.guid) {
		UserAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	} else {
		UserAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		UserAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.remove = function() {
		UserAdminService.remove({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};
	
	$scope.changepswd = function() {
		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			UserAdminService.pswdreset({guid: $routeParams.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
				$scope.pswd = {n: null, c: null, o: null};
			});
		}
	};	

}]);

controllers.controller('UserSearchAdminCtrl', ['$scope','UserAdminService', 
	function($scope, UserAdminService) {

	function _startup() {
		UserAdminService.all({}, function(response) {
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
			return !!(((row.name !== null && row.name.indexOf(tmp) !== -1)));
		}
		return true;
	};
}]);