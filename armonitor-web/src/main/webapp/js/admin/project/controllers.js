controllers.controller('ProjectAdminCtrl', function($scope, $routeParams, ProjectAdminService, CommonService, BTSAdminService) {

	$scope.bts = {selected: null};
	
	if ($routeParams.guid) {
		ProjectAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
			ProjectAdminService.bts({guid: $routeParams.guid}, function(response) {
				$scope.bts.selected = response.guid;				
			});
		});
	} else {
		ProjectAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	BTSAdminService.list({}, function(response) {
		$scope.btslist = response;		
	});
	
	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		ProjectAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
			CommonService.updateMsg();
		});
	};

	$scope.delete = function() {
		ProjectAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};
	
	$scope.addbts = function() {
		ProjectAdminService.addbts({guid: $routeParams.guid, id: $scope.bts.selected}, function(response) {
			
		});
	};

});

controllers.controller('ProjectSearchAdminCtrl', function($scope, ProjectAdminService) {

	function _startup() {
		ProjectAdminService.all({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();

	$scope.reload = function() {
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
});