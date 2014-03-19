
controllers.controller('SystemAppAdminCtrl', function($scope, $routeParams, SystemAdminService, AppAdminService) {

	$scope.item = {selected: null};
	var _load = true;

	$scope.load = function() {

		if (_load) {
			AppAdminService.list({}, function(response) {
				$scope.list = response;
			});
			SystemAdminService.app({guid: $routeParams.guid}, function(response) {
				$scope.item.selected = response.guid;
			});
		}
		_load = false;
	};

	$scope.add = function() {
		AppAdminService.addapp({guid: $routeParams.guid, id: $scope.item.selected}, function(response) {

		});
	};
});

controllers.controller('SystemSearchAdminCtrl', function($scope, SystemAdminService) {

	function _startup() {
		SystemAdminService.all({}, function(response) {
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